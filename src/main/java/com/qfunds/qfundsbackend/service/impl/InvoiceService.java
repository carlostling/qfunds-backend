package com.qfunds.qfundsbackend.service.impl;

import com.qfunds.qfundsbackend.error.EntityDoesNotExistException;
import com.qfunds.qfundsbackend.model.*;
import com.qfunds.qfundsbackend.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceBidService bidService;

    @Autowired
    private UserService userService;

    public Invoice saveInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public void checkInvoicesPassDeadline() {
        List<Invoice> toDeactivate = invoiceRepository
                .findByDeadlineLessThanAndStatus(LocalDateTime.now(), InvoiceStatus.ACTIVE);
        for (Invoice invoice : toDeactivate) {
            System.out.println(invoice.getIssuer().getName() + " Passed deadline by: " +
                    ChronoUnit.SECONDS.between(invoice.getDeadline(), LocalDateTime.now())
                    + " seconds, deactivating");
            if (hasWinner(invoice)) {
                invoice.setStatus(InvoiceStatus.WON);
            } else {
                invoice.setStatus(InvoiceStatus.NO_WINNER);
            }
        }
        invoiceRepository.saveAll(toDeactivate);
    }

    private boolean hasWinner(Invoice invoice) {
        return invoice.getLeadingBid() != null;
    }

    public Invoice placeBid(@RequestBody InvoiceBid bid) throws EntityDoesNotExistException {
        Optional<Invoice> invoiceOpt = invoiceRepository.findById(bid.getInvoiceId());
        if (invoiceOpt.isEmpty()) {
            throw new EntityDoesNotExistException("Bid not found", InvoiceBid.class);
        }
        Invoice invoice = invoiceOpt.get();
        //No current bid, make it leading
        if (invoice.getLeadingBid() == null) {
            invoice.setLeadingBid(bid);
            invoice.addBidToHistory(bid);
            saveInvoice(invoice);
            return invoice;
        }
        if (bidService.isLowerBid(bid, invoice.getLeadingBid())) {
            invoice.setLeadingBid(bid);
            invoice.addBidToHistory(bid);
            saveInvoice(invoice);
        }
        return invoice;
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public List<Invoice> getInvoicesByProps(String search, InvoiceStatus status, Company company, Double lessThanAmount, Boolean hasLeadingBid) {
        return invoiceRepository.findInvoiceByProps(search, status, company, lessThanAmount, hasLeadingBid);
    }

    public List<Invoice> getInvolvedInvoices(String userId) {
        Optional<User> optUser = userService.getUserById(userId);
        if (optUser.isEmpty()) {
            throw new EntityDoesNotExistException("User not found", User.class);
        }
        User user = optUser.get();
        List<Invoice> list = invoiceRepository.findInvoiceWhereCompanyNameInBidHistory(user.getCompany());
        return list;
    }
}

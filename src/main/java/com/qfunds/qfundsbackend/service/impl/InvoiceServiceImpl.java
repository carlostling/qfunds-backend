package com.qfunds.qfundsbackend.service.impl;

import com.qfunds.qfundsbackend.error.EntityDoesNotExistException;
import com.qfunds.qfundsbackend.model.*;
import com.qfunds.qfundsbackend.repository.InvoiceRepository;
import com.qfunds.qfundsbackend.service.BidService;
import com.qfunds.qfundsbackend.service.InvoiceService;
import com.qfunds.qfundsbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    private BidService bidService;

    @Autowired
    private UserService userService;

    @Override
    public Invoice saveInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
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

    @Override
    public Invoice placeBid(@RequestBody Bid bid) throws EntityDoesNotExistException {
        Optional<Invoice> invoiceOpt = invoiceRepository.findById(bid.getInvoiceId());
        if (invoiceOpt.isEmpty()) {
            throw new EntityDoesNotExistException("Bid not found", Bid.class);
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

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public List<Invoice> getInvoicesByProps(String search, InvoiceStatus status, Company company, Double lessThanAmount, Boolean hasLeadingBid) {
        return invoiceRepository.findInvoiceByProps(search, status, company, lessThanAmount, hasLeadingBid);
    }

    @Override
    public List<Invoice> getInvolvedInvoices(String userId) {
        Optional<User> optUser = userService.getUserById(userId);
        if (optUser.isEmpty()) {
            throw new EntityDoesNotExistException("User not found", User.class);
        }
        User user = optUser.get();
        List<Invoice> list = invoiceRepository.findInvoiceWhereCompanyNameInBidHistoryAndStatusIsActive(user.getCompany());
        return list;
    }

    @Override
    public List<Invoice> getWonInvoices(String userId) {
        Optional<User> optUser = userService.getUserById(userId);
        if (optUser.isEmpty()) {
            throw new EntityDoesNotExistException("User not found", User.class);
        }
        User user = optUser.get();
        return invoiceRepository.findInvoiceWhereCompanyNameInLeadingBidAndStatusIsWon(user.getCompany());
    }

    @Override
    public Invoice getInvoiceById(String invoiceId) {
        Optional<Invoice> optInvoice = invoiceRepository.findById(invoiceId);
        if (optInvoice.isEmpty())
            throw new EntityDoesNotExistException("Invoice not found", Invoice.class);

        return optInvoice.get();
    }

}

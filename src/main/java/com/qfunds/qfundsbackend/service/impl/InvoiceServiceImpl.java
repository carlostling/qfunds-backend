package com.qfunds.qfundsbackend.service.impl;

import com.qfunds.qfundsbackend.exception.ResourceNotFoundException;
import com.qfunds.qfundsbackend.model.Bid;
import com.qfunds.qfundsbackend.model.Invoice;
import com.qfunds.qfundsbackend.model.InvoiceStatus;
import com.qfunds.qfundsbackend.repository.InvoiceRepository;
import com.qfunds.qfundsbackend.service.BidService;
import com.qfunds.qfundsbackend.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Override
    public Invoice saveInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

   @Override
   public void checkInvoicesPassDeadline(){
        List<Invoice> toDeactivate = invoiceRepository
                .findByDeadlineLessThanAndStatus(LocalDateTime.now(), InvoiceStatus.ACTIVE);
        for (Invoice invoice : toDeactivate ) {
           System.out.println(invoice.getSeller().getName() + " Passed deadline by: " +
                   ChronoUnit.SECONDS.between(invoice.getDeadline(), LocalDateTime.now())
                    + " seconds, deactivating");
            if (hasWinner(invoice)) {
                invoice.setStatus(InvoiceStatus.WON);
            }
            else{
                invoice.setStatus(InvoiceStatus.NO_WINNER);
            }
        }
        invoiceRepository.saveAll(toDeactivate);
    }

    private boolean hasWinner(Invoice invoice) {
        return invoice.getLeadingBid() != null;
    }

    @Override
    public void placeBid(Bid bid) throws ResourceNotFoundException {
        Optional<Invoice> invoiceOpt = invoiceRepository.findById(bid.getInvoiceId());
        if (invoiceOpt.isEmpty()) {
            throw new ResourceNotFoundException("Invoice not found");
        }
        Invoice invoice = invoiceOpt.get();
       //No current bid, make it leading
        if(invoice.getLeadingBid() == null){
            invoice.setLeadingBid(bid);
            saveInvoice(invoice);
            return;
        }
        if(bidService.isHigherBid(bid, invoice.getLeadingBid())){
            invoice.setLeadingBid(bid);
            saveInvoice(invoice);
        }
    }

}

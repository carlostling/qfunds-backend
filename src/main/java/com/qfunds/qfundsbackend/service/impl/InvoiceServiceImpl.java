package com.qfunds.qfundsbackend.service.impl;

import com.qfunds.qfundsbackend.exception.ResourceNotFoundException;
import com.qfunds.qfundsbackend.model.Bid;
import com.qfunds.qfundsbackend.model.Invoice;
import com.qfunds.qfundsbackend.repository.InvoiceRepository;
import com.qfunds.qfundsbackend.service.BidService;
import com.qfunds.qfundsbackend.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void placeBid(Bid bid) throws ResourceNotFoundException {
        Optional<Invoice> invoiceOpt = invoiceRepository.findById(bid.getInvoiceId());
        if (invoiceOpt.isEmpty()) {
            throw new ResourceNotFoundException("Invoice not found");
        }
        Invoice invoice = invoiceOpt.get();
        if(bidService.isHigherBid(bid, invoice.getLeadingBid())){
            invoice.setLeadingBid(bid);
            invoice.setLeaderId(bid.getBidderId());
        }
    }

}

package com.qfunds.qfundsbackend.service;

import com.qfunds.qfundsbackend.exception.ResourceNotFoundException;
import com.qfunds.qfundsbackend.model.Bid;
import com.qfunds.qfundsbackend.model.Invoice;

import java.util.List;

public interface InvoiceService {

    Invoice saveInvoice(Invoice invoice);

    void checkInvoicesPassDeadline();

    Invoice placeBid(Bid bid) throws ResourceNotFoundException;

    List<Invoice> getAllInvoices();
}

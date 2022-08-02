package com.qfunds.qfundsbackend.service;

import com.qfunds.qfundsbackend.error.EntityDoesNotExistException;
import com.qfunds.qfundsbackend.model.*;

import java.util.List;

public interface InvoiceService {

    Invoice saveInvoice(Invoice invoice);

    void checkInvoicesPassDeadline();

    Invoice placeBid(Bid bid) throws EntityDoesNotExistException;

    List<Invoice> getAllInvoices();

    List<Invoice> getInvoicesByProps(InvoiceStatus status, Company seller,
                                     Double lessThanAmount, Boolean hasLeadingBid);

    List<Invoice> getInvolvedInvoices(String user);
}

package com.qfunds.qfundsbackend.repository.custom;

import com.qfunds.qfundsbackend.model.Invoice;
import com.qfunds.qfundsbackend.model.InvoiceStatus;
import com.qfunds.qfundsbackend.model.Seller;
import java.util.List;

public interface InvoiceCustomRepository {

    public List<Invoice> findInvoiceByProps(InvoiceStatus status, Seller seller,
                                            Double lessThanAmount, Boolean hasLeadingBid);
}

package com.qfunds.qfundsbackend.repository.custom;

import com.qfunds.qfundsbackend.model.Company;
import com.qfunds.qfundsbackend.model.Invoice;
import com.qfunds.qfundsbackend.model.InvoiceStatus;
import java.util.List;

public interface InvoiceCustomRepository {

    public List<Invoice> findInvoiceByProps(InvoiceStatus status, Company company,
                                            Double lessThanAmount, Boolean hasLeadingBid);
}

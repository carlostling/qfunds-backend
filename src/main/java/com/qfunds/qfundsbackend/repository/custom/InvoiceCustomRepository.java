package com.qfunds.qfundsbackend.repository.custom;

import com.qfunds.qfundsbackend.model.Company;
import com.qfunds.qfundsbackend.model.Invoice;
import com.qfunds.qfundsbackend.model.InvoiceStatus;
import java.util.List;

public interface InvoiceCustomRepository {

    List<Invoice> findInvoiceByProps(InvoiceStatus status, Company company,
                                            Double lessThanAmount, Boolean hasLeadingBid);
    List<Invoice> findInvoiceWhereCompanyNameInBidHistory(String companyName);

    List<Invoice> findInvoiceWhereCompanyNameInLeadingBid(String company);
}


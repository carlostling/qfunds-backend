package com.qfunds.qfundsbackend.repository.custom;

import com.qfunds.qfundsbackend.model.Company;
import com.qfunds.qfundsbackend.model.Invoice;
import com.qfunds.qfundsbackend.model.InvoiceStatus;
import java.util.List;

public interface InvoiceCustomRepository{

    List<Invoice> findInvoiceByProps(String search, InvoiceStatus status, Company company,
                                     Double lessThanAmount, Boolean hasLeadingBid);
    List<Invoice> findInvoiceWhereCompanyNameInBidHistoryAndStatusIsActive(String companyName);

    List<Invoice> findInvoiceWhereCompanyNameInLeadingBidAndStatusIsWon(String company);
}


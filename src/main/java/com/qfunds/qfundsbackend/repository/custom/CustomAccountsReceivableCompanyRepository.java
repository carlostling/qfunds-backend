package com.qfunds.qfundsbackend.repository.custom;
import com.qfunds.qfundsbackend.model.AccountsReceivableCompany;
import com.qfunds.qfundsbackend.model.Company;
import com.qfunds.qfundsbackend.model.Invoice;
import com.qfunds.qfundsbackend.model.InvoiceStatus;

import java.util.List;

public interface CustomAccountsReceivableCompanyRepository {
    List<AccountsReceivableCompany> findAccountsReceivableCompanyByProps(String search, InvoiceStatus status, Company company,
                                     Double lessThanAmount, Boolean hasLeadingBid);
    List<AccountsReceivableCompany> findAccountsReceivableCompanyWhereCompanyNameInBidHistory(String companyName);

    List<AccountsReceivableCompany> findArcsByProps(String search, InvoiceStatus status, Company company, Double lessThanAmount, Boolean hasLeadingBid);
}

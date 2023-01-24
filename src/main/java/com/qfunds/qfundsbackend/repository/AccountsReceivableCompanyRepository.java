package com.qfunds.qfundsbackend.repository;

import com.qfunds.qfundsbackend.model.AccountsReceivableCompany;
import com.qfunds.qfundsbackend.repository.custom.CustomAccountsReceivableCompanyRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountsReceivableCompanyRepository extends MongoRepository<AccountsReceivableCompany, String>, CustomAccountsReceivableCompanyRepository {
}

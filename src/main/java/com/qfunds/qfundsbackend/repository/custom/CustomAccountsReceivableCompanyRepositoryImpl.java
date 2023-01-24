package com.qfunds.qfundsbackend.repository.custom;

import com.qfunds.qfundsbackend.model.AccountsReceivableCompany;
import com.qfunds.qfundsbackend.model.Company;
import com.qfunds.qfundsbackend.model.Invoice;
import com.qfunds.qfundsbackend.model.InvoiceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


@Repository
public class CustomAccountsReceivableCompanyRepositoryImpl implements CustomAccountsReceivableCompanyRepository{
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<AccountsReceivableCompany> findAccountsReceivableCompanyByProps(String search, InvoiceStatus status, Company company, Double lessThanAmount, Boolean hasLeadingBid) {
        final Query query = new Query();
        final List<Criteria> criteria = new ArrayList<>();

        if (search != null) {
            criteria.add(Criteria.where("issuer.name").regex(Pattern.compile(search, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
            criteria.add(Criteria.where("receiver.name").regex(Pattern.compile(search, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
            criteria.add(Criteria.where("issuer.orgNumber").is(search));
            criteria.add(Criteria.where("receiver.orgNumber").is(search));
        }

        if (status != null)
            criteria.add(Criteria.where("status").is(status));
        if (company != null)
            criteria.add(Criteria.where("company").is(company));
        if (lessThanAmount != null)
            criteria.add(Criteria.where("amount").lte(lessThanAmount));
        if (hasLeadingBid != null)
            criteria.add(Criteria.where("leadingBid").exists(hasLeadingBid));

        if (!criteria.isEmpty())
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
        return mongoTemplate.find(query, AccountsReceivableCompany.class);
    }

    @Override
    public List<AccountsReceivableCompany> findAccountsReceivableCompanyWhereCompanyNameInBidHistory(String companyName){
        final Query query = new Query();
        query.addCriteria(Criteria.where("bidHistory")
                .elemMatch(Criteria.where("buyerCompany")
                        .is(companyName)));
        return mongoTemplate.find(query, AccountsReceivableCompany.class);
    }
}


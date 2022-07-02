package com.qfunds.qfundsbackend.repository.custom;

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


@Repository
public class InvoiceCustomRepositoryImpl implements InvoiceCustomRepository{
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<Invoice> findInvoiceByProps(InvoiceStatus status, Company company, Double lessThanAmount, Boolean hasLeadingBid) {
        final Query query = new Query();
        final List<Criteria> criteria = new ArrayList<>();
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
        return mongoTemplate.find(query, Invoice.class);
    }
}


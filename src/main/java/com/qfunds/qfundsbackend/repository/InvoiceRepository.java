package com.qfunds.qfundsbackend.repository;

import com.qfunds.qfundsbackend.model.Invoice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InvoiceRepository extends MongoRepository<Invoice, String> {
}
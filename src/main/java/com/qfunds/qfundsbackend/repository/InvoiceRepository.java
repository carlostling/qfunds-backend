package com.qfunds.qfundsbackend.repository;

import com.qfunds.qfundsbackend.model.Invoice;
import com.qfunds.qfundsbackend.model.InvoiceStatus;
import com.qfunds.qfundsbackend.repository.custom.InvoiceCustomRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface InvoiceRepository extends MongoRepository<Invoice, String>, InvoiceCustomRepository {

    List<Invoice> findByDeadlineLessThanAndStatus(LocalDateTime now, InvoiceStatus status);

}

package com.qfunds.qfundsbackend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;


@Document("invoices")
@Data
public class Invoice {
    @Id
    private String id;

    private InvoiceStatus status;

    private LocalDateTime deadline;

    private Company seller;

    private Company buyer;

    private Double amount;

    private List<Bid> bidHistory;

    private Bid leadingBid;

}

package com.qfunds.qfundsbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Document("invoices")
@Data
public class Invoice {
    @Id
    private String id;

    private InvoiceStatus status;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deadline;

    @JsonFormat(pattern="yyyy-MM-dd", shape=JsonFormat.Shape.STRING)
    private LocalDate issueDate;

    @JsonFormat(pattern="yyyy-MM-dd", shape=JsonFormat.Shape.STRING)
    private LocalDate expiryDate;

    private Company issuer;

    private Company receiver;

    private Double rating;

    private Double amount;

    private List<Bid> bidHistory;

    private Bid leadingBid;

}

package com.qfunds.qfundsbackend.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.servlet.View;

import java.util.List;


enum InvoiceStatus {
    NEW,
    IN_BIDDING,
    COMPLETE
}
@Getter
@Setter
@Document("invoices")
@Data
public class Invoice {
    @Id
    private String id;

    private InvoiceStatus status;

    private Double amount;

    private List<Double> bids;
}

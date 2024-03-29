package com.qfunds.qfundsbackend.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("invoice_bids")
public class InvoiceBid {
    @Id
    private String id;

    private String bidderId;

    private String buyerCompany;

    private String invoiceId;

    private Double bidAmount;
}

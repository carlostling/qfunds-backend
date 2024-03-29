package com.qfunds.qfundsbackend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("bids")
public class Bid {
    @Id
    private String id;

    private String bidderId;

    private String buyerCompany;

    private String accountsReceivableCompanyId;

    private Double bidAmount;
}

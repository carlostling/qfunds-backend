package com.qfunds.qfundsbackend.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document("arcs")
public class AccountsReceivableCompany {

    private String name;
    private String orgNumber;
    private String description;
    private Double turnover;
    private Integer dueDate;
    private Double operatingProfit;
    private InvoiceStatus status;
    private LocalDateTime deadline;

    private User winner;
    private List<Bid> bidHistory;
    private Bid leadingBid;

    public void addBidToHistory(Bid bid) {
        if(bidHistory == null){
            bidHistory = new ArrayList<Bid>();
        }
        bidHistory.add(bid);
    }


}

package com.qfunds.qfundsbackend.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Document("arcs")
public class AccountsReceivableCompany {

    private Strnig name;
    private String orgNumber;
    private String description;
    private Double turnover;
    private Integer dueDate;
    private Double operatingProfit;

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

package com.qfunds.qfundsbackend.service.impl;

import com.qfunds.qfundsbackend.model.Bid;
import com.qfunds.qfundsbackend.model.InvoiceBid;
import org.springframework.stereotype.Service;

@Service
public class InvoiceBidService {
    public boolean isLowerBid(InvoiceBid bid1, InvoiceBid bid2) {
        return bid1.getBidAmount() < bid2.getBidAmount();
    }
}

package com.qfunds.qfundsbackend.service.impl;

import com.qfunds.qfundsbackend.model.Bid;
import org.springframework.stereotype.Service;

@Service
public class BidService {
    public boolean isLowerBid(Bid bid1, Bid bid2) {
        return bid1.getBidAmount() < bid2.getBidAmount();
    }
}

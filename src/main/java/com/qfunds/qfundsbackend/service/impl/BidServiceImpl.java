package com.qfunds.qfundsbackend.service.impl;

import com.qfunds.qfundsbackend.model.Bid;
import com.qfunds.qfundsbackend.service.BidService;

public class BidServiceImpl implements BidService {
    @Override
    public boolean isHigherBid(Bid bid1, Bid bid2) {
        return bid1.getBidAmount() > bid2.getBidAmount();
    }
}

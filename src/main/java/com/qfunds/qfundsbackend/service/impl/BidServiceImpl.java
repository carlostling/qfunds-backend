package com.qfunds.qfundsbackend.service.impl;

import com.qfunds.qfundsbackend.model.Bid;
import com.qfunds.qfundsbackend.service.BidService;
import org.springframework.stereotype.Service;

@Service
public class BidServiceImpl implements BidService {
    @Override
    public boolean isHigherBid(Bid bid1, Bid bid2) {
        return bid1.getBidAmount() > bid2.getBidAmount();
    }
}

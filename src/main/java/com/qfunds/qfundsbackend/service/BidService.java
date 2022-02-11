package com.qfunds.qfundsbackend.service;

import com.qfunds.qfundsbackend.model.Bid;

public interface BidService {

    boolean isLowerBid(Bid bid1, Bid bid2);

}

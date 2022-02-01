package com.qfunds.qfundsbackend.service;

import com.qfunds.qfundsbackend.model.Bid;

public interface BidService {

    boolean isHigherBid(Bid bid1, Bid bid2);

}

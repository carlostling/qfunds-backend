package com.qfunds.qfundsbackend.service.impl;

import com.qfunds.qfundsbackend.model.*;
import com.qfunds.qfundsbackend.repository.AccountsReceivableCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class AccountsReceivableCompanyService{

    @Autowired
    AccountsReceivableCompanyRepository accountsReceivableCompanyRepository;

    @Autowired
    private BidService bidService;

    @Autowired
    private UserService userService;

    public AccountsReceivableCompany saveAccountsReceivableCompany(AccountsReceivableCompany arc) {
        return accountsReceivableCompanyRepository.save(arc);
    }

    /**public void checkInvoicesPassDeadline() {
        List<Invoice> toDeactivate = accountsReceivableCompanyRepository
                .findByDeadlineLessThanAndStatus(LocalDateTime.now(), InvoiceStatus.ACTIVE);
        for (Invoice invoice : toDeactivate) {
            System.out.println(invoice.getIssuer().getName() + " Passed deadline by: " +
                    ChronoUnit.SECONDS.between(invoice.getDeadline(), LocalDateTime.now())
                    + " seconds, deactivating");
            if (hasWinner(invoice)) {
                invoice.setStatus(InvoiceStatus.WON);
            } else {
                invoice.setStatus(InvoiceStatus.NO_WINNER);
            }
        }
        accountsReceivableCompanyRepository.saveAll(toDeactivate);
    }**/

    private boolean hasWinner(AccountsReceivableCompany arc) {
        return arc.getLeadingBid() != null;
    }

    public AccountsReceivableCompany placeBid(@RequestBody Bid bid) throws NullPointerException {
        Optional<AccountsReceivableCompany> arcOpt = accountsReceivableCompanyRepository.findById(bid.getAccountsReceivableCompanyId());
        if (arcOpt.isEmpty()) {
            throw new NullPointerException();
        }
        AccountsReceivableCompany arc = arcOpt.get();
        //No current bid, make it leading
        if (arc.getLeadingBid() == null) {
            arc.setLeadingBid(bid);
            arc.addBidToHistory(bid);
            saveAccountsReceivableCompany(arc);
            return arc;
        }
        if (bidService.isLowerBid(bid, arc.getLeadingBid())) {
            arc.setLeadingBid(bid);
            arc.addBidToHistory(bid);
            saveAccountsReceivableCompany(arc);
        }
        return arc;
    }

    public List<AccountsReceivableCompany> accountsReceivableCompanies() {
        return accountsReceivableCompanyRepository.findAll();
    }

    /*public List<AccountsReceivableCompany> getInvoicesByProps(String search, InvoiceStatus status, Company company, Double lessThanAmount, Boolean hasLeadingBid) {
        return invoiceRepository.findInvoiceByProps(search, status, company, lessThanAmount, hasLeadingBid);
    }*/

    public List<AccountsReceivableCompany> getInvolvedaccountsReceivableCompanies(String userId) {
        Optional<User> optUser = userService.getUserById(userId);
        if (optUser.isEmpty()) {
            throw new NullPointerException();
        }
        User user = optUser.get();
        List<AccountsReceivableCompany> list = accountsReceivableCompanyRepository.findAccountsReceivableCompanyWhereCompanyNameInBidHistory(user.getCompany());
        return list;
    }

    public List<AccountsReceivableCompany> getAllArcs() {
        return accountsReceivableCompanyRepository.findAll();
    }
    public List<AccountsReceivableCompany> getArcsByProps(String search, InvoiceStatus status, Company company, Double lessThanAmount, Boolean hasLeadingBid) {
        return accountsReceivableCompanyRepository.findArcsByProps(search, status, company, lessThanAmount, hasLeadingBid);
    }
}

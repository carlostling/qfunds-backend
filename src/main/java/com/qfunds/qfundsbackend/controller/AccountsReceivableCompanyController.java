package com.qfunds.qfundsbackend.controller;

import com.qfunds.qfundsbackend.error.*;
import com.qfunds.qfundsbackend.model.*;
import com.qfunds.qfundsbackend.service.impl.AccountsReceivableCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/accounts-receivable-company")
public class AccountsReceivableCompanyController {

    @Value("${invoiceDeadlineHours}")
    private Integer DEADLINE_HOURS;
    @Autowired
    private AccountsReceivableCompanyService accountsReceivableCompanyService;

    @PostMapping("/")
    public ResponseEntity<AccountsReceivableCompany> postAccountsReceivableCompany(@RequestBody AccountsReceivableCompany arc) {
        arc.setDeadline(LocalDateTime.now().plusHours(DEADLINE_HOURS));
        arc.setStatus(InvoiceStatus.ACTIVE);
        accountsReceivableCompanyService.saveAccountsReceivableCompany(arc);
        return new ResponseEntity<>(arc, HttpStatus.OK);
    }

    @PostMapping("/bid")
    public ResponseEntity<AccountsReceivableCompany> placeBid(@RequestBody Bid bid) {
        System.out.println(bid);
        try {
            AccountsReceivableCompany arc = accountsReceivableCompanyService.placeBid(bid);
            return new ResponseEntity<>(arc, HttpStatus.OK);
        } catch (EntityDoesNotExistException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /*@GetMapping("/")
    public ResponseEntity<List<AccountsReceivableCompany>> getAllArcs() {
        System.out.println(("GET ARCS"));
        List<AccountsReceivableCompany> arcs = accountsReceivableCompanyService.getAllArcs();
        return new ResponseEntity<>(arcs, HttpStatus.OK);
    }*/

    @GetMapping("/")
    public ResponseEntity<?> getArcsByProperties(@RequestParam(required = false) String search,
                                                     @RequestParam(required = false) InvoiceStatus status,
                                                     @RequestParam(required = false) Company company,
                                                     @RequestParam(required = false) Double lessThanAmount,
                                                     @RequestParam(required = false) Boolean hasLeadingBid) {
        return ResponseEntity.ok().body(
                accountsReceivableCompanyService.getArcsByProps(
                        search, status, company, lessThanAmount, hasLeadingBid));
    }
}


/*
     * Returns a list of invoices where the user and/or its company has already placed a bid.
     *//*

    @GetMapping("/involved/{userId}")
    public ResponseEntity<?> getInvolvedInvoices(@PathVariable String userId){
        return ResponseEntity.ok().body(invoiceService.getInvolvedInvoices(userId));
    }

    @PostMapping("/test")
    public void process(@RequestBody Map<String, Object> payload)
            throws Exception {

        System.out.println(payload);

    }
}
*/


/*
package com.qfunds.qfundsbackend.controller;


import com.qfunds.qfundsbackend.error.EntityDoesNotExistException;
import com.qfunds.qfundsbackend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping(value = "api/v1/invoice")
public class InvoiceController {

    @Value("${invoiceDeadlineHours}")
    private Integer DEADLINE_HOURS;
    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/")
    public ResponseEntity<Invoice> postInvoice(@RequestBody Invoice invoice){
        invoice.setDeadline(LocalDateTime.now().plusHours(DEADLINE_HOURS));
        invoice.setStatus(InvoiceStatus.ACTIVE);
        invoiceService.saveInvoice(invoice);
        return new ResponseEntity<>(invoice, HttpStatus.OK);
    }

    @GetMapping("/{invoiceId}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable String invoiceId){
        return new ResponseEntity(invoiceService.getInvoiceById(invoiceId), HttpStatus.OK);
    }

    @PostMapping("/bid")
    public ResponseEntity<Invoice> placeBid(@RequestBody InvoiceBid bid){
        System.out.println(bid);
        try{
            Invoice invoice = invoiceService.placeBid(bid);
            return new ResponseEntity<>(invoice, HttpStatus.OK);
        }
        catch(EntityDoesNotExistException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    */
/**@GetMapping("/")
    public ResponseEntity<List<Invoice>> getAllInvoices(){
        List<Invoice> invoices = invoiceService.getAllInvoices();
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }**//*


    @GetMapping("/")
    public ResponseEntity<?> getInvoicesByProperties(@RequestParam(required = false) String search,
                                                     @RequestParam(required = false) InvoiceStatus status,
                                                     @RequestParam(required = false) Company company,
                                                     @RequestParam(required = false) Double lessThanAmount,
                                                     @RequestParam(required = false) Boolean hasLeadingBid) {
        return ResponseEntity.ok().body(
                        invoiceService.getInvoicesByProps(
                                search, status, company, lessThanAmount, hasLeadingBid));
    }

    */
/**
     * Returns a list of invoices where the user and/or its company has already placed a bid.
     *//*

    @GetMapping("/involved/{userId}")
    public ResponseEntity<?> getInvolvedInvoices(@PathVariable String userId){
        return ResponseEntity.ok().body(invoiceService.getInvolvedInvoices(userId));
    }

    @GetMapping("/won/{userId}")
    public ResponseEntity<?> getWonInvoices(@PathVariable String userId){ //This is not secure
        return ResponseEntity.ok().body(invoiceService.getWonInvoices(userId));
    }

    @PostMapping("/test")
    public void process(@RequestBody Map<String, Object> payload)
            throws Exception {

        System.out.println(payload);

    }
}
*/

package com.qfunds.qfundsbackend.controller;


import com.qfunds.qfundsbackend.exception.ResourceNotFoundException;
import com.qfunds.qfundsbackend.model.Bid;
import com.qfunds.qfundsbackend.model.Invoice;
import com.qfunds.qfundsbackend.model.InvoiceStatus;
import com.qfunds.qfundsbackend.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping(value = "api/v1/invoice")
public class InvoiceController {

    @Value("${invoiceDeadlineHours}")
    private Integer DEADLINE_HOURS;
    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/")
    public ResponseEntity postInvoice(@RequestBody Invoice invoice){
        invoice.setDeadline(LocalDateTime.now().plusHours(DEADLINE_HOURS));
        invoice.setStatus(InvoiceStatus.ACTIVE);
        invoiceService.saveInvoice(invoice);
        return new ResponseEntity("Invoice added successfully.", HttpStatus.OK);
    }

    @PostMapping("/bid")
    public ResponseEntity<Invoice> bid(@RequestBody Bid bid){

        try{
            invoiceService.placeBid(bid);
            return new ResponseEntity("Bid placed successfully.", HttpStatus.OK);
        }
        catch(ResourceNotFoundException e){
            return new ResponseEntity("Invoice for bid not found.", HttpStatus.NOT_FOUND);
        }
    }
}

package com.qfunds.qfundsbackend.controller;


import com.qfunds.qfundsbackend.exception.ResourceNotFoundException;
import com.qfunds.qfundsbackend.model.Bid;
import com.qfunds.qfundsbackend.model.Greeting;
import com.qfunds.qfundsbackend.model.Invoice;
import com.qfunds.qfundsbackend.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping(value = "api/v1/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/")
    public ResponseEntity<Invoice> postInvoice(@RequestBody Invoice invoice){
        invoiceService.saveInvoice(invoice);
        return new ResponseEntity("Invoice added successfully.", HttpStatus.OK);
    }

    @PostMapping("/bid}")
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

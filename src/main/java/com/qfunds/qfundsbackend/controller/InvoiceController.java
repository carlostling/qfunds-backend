package com.qfunds.qfundsbackend.controller;


import com.qfunds.qfundsbackend.model.Greeting;
import com.qfunds.qfundsbackend.model.Invoice;
import com.qfunds.qfundsbackend.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping(value = "api/v1/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "oioi";

    }
    @PostMapping("/")
    public ResponseEntity<Invoice> postInvoice(@RequestBody Invoice invoice) throws URISyntaxException{
        invoiceService.saveInvoice(invoice);
        return ResponseEntity.accepted().build();
    }
}

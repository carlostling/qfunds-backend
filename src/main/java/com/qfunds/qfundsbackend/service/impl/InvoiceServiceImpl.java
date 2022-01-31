package com.qfunds.qfundsbackend.service.impl;

import com.qfunds.qfundsbackend.model.Invoice;
import com.qfunds.qfundsbackend.repository.InvoiceRepository;
import com.qfunds.qfundsbackend.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl implements InvoiceService {

   @Autowired
   InvoiceRepository invoiceRepository;

    @Override
    public Invoice saveInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }
}

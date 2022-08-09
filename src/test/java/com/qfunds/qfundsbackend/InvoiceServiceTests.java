package com.qfunds.qfundsbackend;


import com.qfunds.qfundsbackend.model.Bid;
import com.qfunds.qfundsbackend.model.Invoice;
import com.qfunds.qfundsbackend.model.User;
import com.qfunds.qfundsbackend.service.InvoiceService;
import com.qfunds.qfundsbackend.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;


@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
@SpringBootTest
public class InvoiceServiceTests {

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    UserService userService;

    @Test
    void involvedInvoicesTest(){
        Invoice invoice1 = new Invoice();
        Invoice invoice2 = new Invoice();
        invoice1 = invoiceService.saveInvoice(invoice1);
        invoice2 = invoiceService.saveInvoice(invoice2);
        User user1 = new User();
        user1.setCompany("c1");
        User user2 = new User();
        user2.setCompany("c1");
        User user3 = new User();
        user3.setCompany("c2");
        user1 = userService.saveUser(user1);
        user2 = userService.saveUser(user2);
        user3 = userService.saveUser(user3);

        Bid bid1 = new Bid();
        bid1.setInvoiceId(invoice1.getId());
        bid1.setBuyerCompany(user1.getCompany());
        bid1.setBidAmount(5.0);
        bid1.setBidderId(user1.getId());
        invoiceService.placeBid(bid1);

        List<Invoice> invoicesUser1 = invoiceService.getInvolvedInvoices(user1.getId());
        List<Invoice> invoicesUser2 = invoiceService.getInvolvedInvoices(user2.getId());
        List<Invoice> invoicesUser3 = invoiceService.getInvolvedInvoices(user3.getId());


        boolean contains1 = false;
        boolean contains2 = false;
        boolean contains3 = false;
        for (Invoice invoice:invoicesUser1){
            if (invoice.getId().equals(invoice1.getId())) {
                contains1 = true;
                break;
            }
        }
        for (Invoice invoice:invoicesUser2){
            if (invoice.getId().equals(invoice1.getId())) {
                contains2 = true;
                break;
            }
        }
        for (Invoice invoice:invoicesUser3){
            if (invoice.getId().equals(invoice1.getId())) {
                contains3 = true;
                break;
            }
        }
        assertTrue(contains1);
        assertTrue(contains2);
        assertFalse(contains3);




    }
}

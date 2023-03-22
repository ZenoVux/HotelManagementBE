package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.BookingDetail;
import com.devz.hotelmanagement.entities.InvoiceDetail;
import com.devz.hotelmanagement.services.InvoiceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/invoice-details")
public class InvoiceDetailRestController {

    @Autowired
    private InvoiceDetailService invoiceDetailService;

    @GetMapping
    public List<InvoiceDetail> getAll() {
        return invoiceDetailService.findAll();
    }

    @PostMapping
    public InvoiceDetail create(@RequestBody InvoiceDetail invoiceDetail) {
        return invoiceDetailService.create(invoiceDetail);
    }

    @PutMapping
    public InvoiceDetail update(@RequestBody InvoiceDetail invoiceDetail) {
        return invoiceDetailService.update(invoiceDetail);
    }


    @GetMapping("/invoice-code/{code}")
    public List<InvoiceDetail> findByInvoiceCode(@PathVariable("code") String code) {
        return invoiceDetailService.findByInvoiceCode(code);
    }

}

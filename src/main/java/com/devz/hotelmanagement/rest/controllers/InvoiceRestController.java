package com.devz.hotelmanagement.rest.controllers;

import java.util.List;

import com.devz.hotelmanagement.entities.BookingDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devz.hotelmanagement.entities.Invoice;
import com.devz.hotelmanagement.services.InvoiceService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/invoices")
public class InvoiceRestController {

	@Autowired
    private InvoiceService invoiceService;

    @GetMapping
    public List<Invoice> getAll() {
        return invoiceService.findAll();
    }

    @GetMapping("/{code}")
    public ResponseEntity<Invoice> findByCode(@PathVariable("code") String code) {
        Invoice invoice = invoiceService.findByCode(code);
        if (invoice != null) {
            return ResponseEntity.ok(invoice);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public Invoice create(@RequestBody Invoice invoice) {
        return invoiceService.create(invoice);
    }

    @PutMapping
    public Invoice update(@RequestBody Invoice invoice) {
        return invoiceService.update(invoice);
    }
}

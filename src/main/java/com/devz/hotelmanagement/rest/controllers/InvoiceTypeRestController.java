package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.InvoiceType;
import com.devz.hotelmanagement.services.InvoiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/invoice-types")
public class InvoiceTypeRestController {

    @Autowired
    private InvoiceTypeService invoiceTypeService;

    @GetMapping
    public List<InvoiceType> getAll() {
        return invoiceTypeService.findAll();
    }

    @PostMapping
    public InvoiceType create(@RequestBody InvoiceType invoiceType) {
        return invoiceTypeService.create(invoiceType);
    }

    @PutMapping
    public InvoiceType update(@RequestBody InvoiceType invoiceType) {
        return invoiceTypeService.update(invoiceType);
    }
}

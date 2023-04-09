package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.BookingDetailHistory;
import com.devz.hotelmanagement.entities.InvoiceDetailHistory;
import com.devz.hotelmanagement.services.BookingDetailHistoryService;
import com.devz.hotelmanagement.services.InvoiceDetailHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/invoice-detail-histories")
public class InvoiceDetailHistoryRestController {

    @Autowired
    private InvoiceDetailHistoryService invoiceDetailHistoryService;

    @GetMapping
    public List<InvoiceDetailHistory> getAll(@RequestParam("invoiceDetailId") Optional<Integer> invoiceDetailId) {
        if (invoiceDetailId.isPresent()) {
            return invoiceDetailHistoryService.findAllByInvoiceDetailId(invoiceDetailId.get());
        } else {
            return invoiceDetailHistoryService.findAll();
        }
    }

    @PostMapping
    public InvoiceDetailHistory create(@RequestBody InvoiceDetailHistory invoiceDetailHistory) {
        return invoiceDetailHistoryService.create(invoiceDetailHistory);
    }

    @PutMapping
    public InvoiceDetailHistory update(@RequestBody InvoiceDetailHistory invoiceDetailHistory) {
        return invoiceDetailHistoryService.update(invoiceDetailHistory);
    }

}

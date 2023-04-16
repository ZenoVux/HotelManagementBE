package com.devz.hotelmanagement.rest.controllers;

import java.util.*;

import com.devz.hotelmanagement.models.InvoiceResp;
import com.devz.hotelmanagement.repositories.InvoiceRepository;
import com.devz.hotelmanagement.models.InvoiceStatusCountResp;
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

    @Autowired
    private InvoiceRepository invoiceRepository;


    @GetMapping
    public List<InvoiceResp> findAll() {
        return invoiceService.findByAllResp();
    }

    @GetMapping("/{code}")
    public ResponseEntity<Invoice> findByCode(@PathVariable("code") String code) {
        Invoice invoice = invoiceService.findByCode(code);
        if (invoice != null) {
            return ResponseEntity.ok(invoice);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/status-count")
    public List<InvoiceStatusCountResp> getStatusCount() {
        return invoiceService.getStatusCount();
    }

    @PostMapping
    public Invoice create(@RequestBody Invoice invoice) {
        return invoiceService.create(invoice);
    }

    @PutMapping
    public Invoice update(@RequestBody Invoice invoice) {
        return invoiceService.update(invoice);
    }

    @GetMapping("/totals")
    public ResponseEntity<Map<String, Double>> getTotals() {
        List<Object[]> todayResult = invoiceRepository.getTotalByToday();
        List<Object[]> yesterdayResult = invoiceRepository.getTotalByYesterday();
        Map<String, Double> totals = new HashMap<>();

        for (Object[] result : todayResult) {
            String key = result[0].toString();
            Double value = result[1] != null ? Double.parseDouble(result[1].toString()) : 0.0;
            totals.put(key, value);
        }

        for (Object[] result : yesterdayResult) {
            String key = result[0].toString();
            Double value = result[1] != null ? Double.parseDouble(result[1].toString()) : 0.0;
            totals.put(key, value);
        }

        return new ResponseEntity<>(totals, HttpStatus.OK);
    }

    @GetMapping("/byDate/{startDate}/{endDate}")
    public ResponseEntity<Double> getTotalsByDateRange(@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) {
        Double totalAmount = invoiceRepository.getTotalAmountByDateRange(startDate, endDate);
        if (totalAmount == null) {
            totalAmount = 0.0;
        }
        return new ResponseEntity<>(totalAmount, HttpStatus.OK);
    }

    @GetMapping("/count-by-customer/{peopleId}")
    public Integer countInvoiceByPeopleId(@PathVariable("peopleId") String peopleId) {
        return invoiceService.countInvoiceByPeopleId(peopleId);
    }

}

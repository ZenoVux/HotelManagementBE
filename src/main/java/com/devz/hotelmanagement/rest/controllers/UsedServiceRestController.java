package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.Account;
import com.devz.hotelmanagement.entities.UsedService;
import com.devz.hotelmanagement.services.UsedServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/used-services")
public class UsedServiceRestController {

    @Autowired
    private UsedServiceService usedServiceService;

    @GetMapping
    public List<UsedService> getAll(
            @RequestParam("invoiceDetailId") Optional<Integer> invoiceDetailId,
            @RequestParam("status") Optional<Boolean> status
    ) {
        if (invoiceDetailId.isPresent() && status.isPresent()) {
            return usedServiceService.findAllByInvoiceDetailIdAndStatus(invoiceDetailId.get(), status.get());
        } else if (invoiceDetailId.isPresent()) {
            return usedServiceService.findByInvoiceDetailId(invoiceDetailId.get());
        } else {
            return usedServiceService.findAll();
        }
    }

    @PostMapping
    public ResponseEntity<UsedService> create(@RequestBody UsedService usedService) {
        usedService = usedServiceService.create(usedService);
        if (usedService != null) {
            return ResponseEntity.ok(usedService);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<UsedService> update(@RequestBody UsedService usedService) {
        usedService = usedServiceService.update(usedService);
        if (usedService != null) {
            return ResponseEntity.ok(usedService);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        try {
            usedServiceService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/invoice-detail/{id}")
    public List<UsedService> findAllByInvoiceDetailId(@PathVariable("id") Integer id) {
        return usedServiceService.findByInvoiceDetailId(id);
    }

    @PostMapping("/stop-service/{id}")
    public ResponseEntity<Void> stopService(@PathVariable("id") Integer id) {
        try {
            usedServiceService.stop(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

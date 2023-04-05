package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.Account;
import com.devz.hotelmanagement.entities.UsedService;
import com.devz.hotelmanagement.services.UsedServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/used-services")
public class UsedServiceRestController {

    @Autowired
    private UsedServiceService usedServiceService;

    @GetMapping
    public List<UsedService> getAll() {
        return usedServiceService.findAll();
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
}

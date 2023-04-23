package com.devz.hotelmanagement.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devz.hotelmanagement.entities.HostedAt;
import com.devz.hotelmanagement.services.HostedAtService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/hosted-ats")
public class HostedAtRestController {

    @Autowired
    private HostedAtService hostedAtService;

    @GetMapping
    public List<HostedAt> getAll() {
        return hostedAtService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody HostedAt hostedAt) {
        try {
            hostedAt = hostedAtService.create(hostedAt);
            if (hostedAt != null) {
                return ResponseEntity.status(HttpStatus.OK).body(hostedAt);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping
    public ResponseEntity<HostedAt> update(@RequestBody HostedAt hostedAt) {
        hostedAt = hostedAtService.update(hostedAt);
        if (hostedAt != null) {
            return ResponseEntity.ok(hostedAt);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        try {
            hostedAtService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/invoice-detail/{id}")
    public List<HostedAt> findByInvoiceDetailId(@PathVariable("id") Integer id) {
        return hostedAtService.findByInvoiceDetailId(id);
    }
}

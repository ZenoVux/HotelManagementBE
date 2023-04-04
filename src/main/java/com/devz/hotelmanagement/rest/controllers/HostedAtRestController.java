package com.devz.hotelmanagement.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    public HostedAt create(@RequestBody HostedAt hostedAt) {
        return hostedAtService.create(hostedAt);
    }

    @PutMapping
    public HostedAt update(@RequestBody HostedAt hostedAt) {
        return hostedAtService.update(hostedAt);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        hostedAtService.delete(id);
    }

    @GetMapping("/invoice-detail/{id}")
    public List<HostedAt> findByInvoiceDetailId(@PathVariable("id") Integer id) {
        return hostedAtService.findByInvoiceDetailId(id);
    }
}

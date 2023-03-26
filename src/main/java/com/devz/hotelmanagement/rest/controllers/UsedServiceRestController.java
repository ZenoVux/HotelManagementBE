package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.UsedService;
import com.devz.hotelmanagement.services.UsedServiceService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UsedService create(@RequestBody UsedService usedService) {
        return usedServiceService.create(usedService);
    }

    @PutMapping
    public UsedService update(@RequestBody UsedService usedService) {
        return usedServiceService.update(usedService);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        usedServiceService.delete(id);
    }

    @GetMapping("/booking-detail-code/{code}")
    public List<UsedService> findAllByBookingDetailCode(@PathVariable("code") String code) {
        return usedServiceService.findAllByBookingDetailCode(code);
    }

    @GetMapping("/booking-detail/{id}")
    public List<UsedService> findAllByBookingDetailId(@PathVariable("id") Integer id) {
        return usedServiceService.findAllByBookingDetailId(id);
    }

    @GetMapping("/invoice-detail/{id}")
    public List<UsedService> findAllByInvoiceDetailId(@PathVariable("id") Integer id) {
        return usedServiceService.findByInvoiceDetailId(id);
    }
}

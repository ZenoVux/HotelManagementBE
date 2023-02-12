package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.UsedService;
import com.devz.hotelmanagement.services.UsedServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/used-service")
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

}

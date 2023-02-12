package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.ServiceType;
import com.devz.hotelmanagement.services.ServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/service-types")
public class ServiceTypeRestController {
    @Autowired
    private ServiceTypeService serviceTypeService;

    @GetMapping
    public List<ServiceType> getAll() {
        return serviceTypeService.findAll();
    }

    @PostMapping
    public ServiceType create(@RequestBody ServiceType serviceType) {
        return serviceTypeService.create(serviceType);
    }

    @PutMapping
    public ServiceType update(@RequestBody ServiceType serviceType) {
        return serviceTypeService.update(serviceType);
    }
}

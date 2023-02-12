package com.devz.hotelmanagement.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devz.hotelmanagement.entities.CustomerType;
import com.devz.hotelmanagement.services.CustomerTypeService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/customer-types")
public class CustomerTypeRestController {

    @Autowired
    private CustomerTypeService customerTypeService;

    @GetMapping
    public List<CustomerType> getAll() {
        return customerTypeService.findAll();
    }

    @PostMapping
    public CustomerType create(@RequestBody CustomerType customerType) {
        return customerTypeService.create(customerType);
    }

    @PutMapping
    public CustomerType update(@RequestBody CustomerType customerType) {
        return customerTypeService.update(customerType);
    }
}

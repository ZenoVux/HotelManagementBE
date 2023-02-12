package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.BookingDetail;
import com.devz.hotelmanagement.entities.CustomerImage;
import com.devz.hotelmanagement.services.BookingDetailService;
import com.devz.hotelmanagement.services.CustomerImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/customerimages")
public class CustomerImageRestController {
    @Autowired
    private CustomerImageService customerImageService;

    @GetMapping
    public List<CustomerImage> getAll() {
        return customerImageService.findAll();
    }

    @PostMapping
    public CustomerImage create(@RequestBody CustomerImage customerImage) {
        return customerImageService.create(customerImage);
    }

    @PutMapping
    public CustomerImage update(@RequestBody CustomerImage customerImage) {
        return customerImageService.update(customerImage);
    }
}

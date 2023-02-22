package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.Customer;
import com.devz.hotelmanagement.entities.PaymentMethod;
import com.devz.hotelmanagement.services.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/payment-methods")
public class PaymentMethodRestController {

    @Autowired
    private PaymentMethodService paymentMethodService;

    @GetMapping
    public List<PaymentMethod> getAll() {
        return paymentMethodService.findAll();
    }
    @GetMapping("/{id}")
    public PaymentMethod getCustomerById(@PathVariable int id) {
        PaymentMethod paymentMethod = paymentMethodService.findById(id);
        return paymentMethod;
    }
    @PostMapping
    public PaymentMethod create(@RequestBody PaymentMethod paymentMethod) {
        return paymentMethodService.create(paymentMethod);
    }

    @PutMapping
    public PaymentMethod update(@RequestBody PaymentMethod paymentMethod) {
        return paymentMethodService.update(paymentMethod);
    }
}

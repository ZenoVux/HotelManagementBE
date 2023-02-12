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
}

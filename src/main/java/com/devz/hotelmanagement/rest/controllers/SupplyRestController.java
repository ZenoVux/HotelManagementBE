package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.Supply;
import com.devz.hotelmanagement.services.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/supplies")
public class SupplyRestController {

    @Autowired
    private SupplyService supplyService;

    @GetMapping
    public List<Supply> getAll() {
        return supplyService.findAll();
    }

    @GetMapping("/{id}")
    public Supply getOne(@PathVariable("id") Integer id) {
        return supplyService.findById(id);
    }
    @PostMapping
    public Supply create(@RequestBody Supply supply) {
        return supplyService.create(supply);
    }

    @PutMapping
    public Supply update(@RequestBody Supply supply) {
        return supplyService.update(supply);
    }

}

package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.SupplyType;
import com.devz.hotelmanagement.services.SupplyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/supply-types")
public class SupplyTypeRestController {

    @Autowired
    private SupplyTypeService supplyTypeService;

    @GetMapping
    public List<SupplyType> getAll() {
        return supplyTypeService.findAll();
    }

    @GetMapping("/{id}")
    public SupplyType getOne(@PathVariable("id") Integer id) {
        return supplyTypeService.findById(id);
    }
    @PostMapping
    public SupplyType create(@RequestBody SupplyType supplyType) {
        return supplyTypeService.create(supplyType);
    }

    @PutMapping
    public SupplyType update(@RequestBody SupplyType supplyType) {
        return supplyTypeService.update(supplyType);
    }

}

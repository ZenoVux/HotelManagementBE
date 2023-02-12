package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.BedType;
import com.devz.hotelmanagement.services.BedTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/bed-types")
public class BedTypeRestController {

    @Autowired
    private BedTypeService bedTypeService;

    @GetMapping
    public List<BedType> getAll() {
        return bedTypeService.findAll();
    }

    @PostMapping
    public BedType create(@RequestBody BedType bedType) {
        return bedTypeService.create(bedType);
    }

    @PutMapping
    public BedType update(@RequestBody BedType bedType) {
        return bedTypeService.update(bedType);
    }

}

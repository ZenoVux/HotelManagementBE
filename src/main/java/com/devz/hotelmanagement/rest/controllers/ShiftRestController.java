package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.Shift;
import com.devz.hotelmanagement.services.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/shifts")
public class ShiftRestController {

    @Autowired
    private ShiftService shiftService;

    @GetMapping
    public List<Shift> getAll() {
        return shiftService.findAll();
    }

    @PostMapping
    public Shift create(@RequestBody Shift shift) {
        return shiftService.create(shift);
    }

    @PutMapping
    public Shift update(@RequestBody Shift shift) {
        return shiftService.update(shift);
    }

}

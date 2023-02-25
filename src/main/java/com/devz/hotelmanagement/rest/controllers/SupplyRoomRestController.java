package com.devz.hotelmanagement.rest.controllers;


import com.devz.hotelmanagement.entities.SupplyRoom;
import com.devz.hotelmanagement.services.SupplyRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/supply-rooms")
public class SupplyRoomRestController {

    @Autowired
    private SupplyRoomService supplyRoomService;

    @GetMapping
    public List<SupplyRoom> getAll() {
        return supplyRoomService.findAll();
    }

    @PostMapping
    public SupplyRoom create(@RequestBody SupplyRoom supplyRoom) {
        return supplyRoomService.create(supplyRoom);
    }

    @PutMapping
    public SupplyRoom update(@RequestBody SupplyRoom supplyRoom) {
        return supplyRoomService.update(supplyRoom);
    }
}

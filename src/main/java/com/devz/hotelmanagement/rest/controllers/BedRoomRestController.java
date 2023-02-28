package com.devz.hotelmanagement.rest.controllers;


import com.devz.hotelmanagement.entities.BedRoom;
import com.devz.hotelmanagement.services.BedRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/bed-rooms")
public class BedRoomRestController {

    @Autowired
    private BedRoomService bedRoomService;

    @GetMapping
    public List<BedRoom> getAll() {
        return bedRoomService.findAll();
    }

    @PostMapping
    public BedRoom create(@RequestBody BedRoom bedRoom) {
        return bedRoomService.create(bedRoom);
    }

    @PutMapping
    public BedRoom update(@RequestBody BedRoom bedRoom) {
        return bedRoomService.update(bedRoom);
    }

}

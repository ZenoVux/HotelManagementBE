package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.RoomType;
import com.devz.hotelmanagement.services.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/room-types")
public class RoomTypeRestController {
    @Autowired
    RoomTypeService roomTypeService;

    @GetMapping
    public List<RoomType> getAll() {
        return roomTypeService.findAll();
    }

    @PostMapping
    public RoomType create(@RequestBody RoomType roomType) {
        return roomTypeService.create(roomType);
    }

    @PutMapping
    public RoomType update(@RequestBody RoomType roomType) {
        return roomTypeService.update(roomType);
    }
}

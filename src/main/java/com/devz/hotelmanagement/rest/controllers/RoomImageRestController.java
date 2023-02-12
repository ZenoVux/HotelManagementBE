package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.RoomImage;
import com.devz.hotelmanagement.services.RoomImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/room-images")
public class RoomImageRestController {

    @Autowired
    private RoomImageService roomImageService;

    @GetMapping
    public List<RoomImage> getAll() {
        return roomImageService.findAll();
    }

    @PostMapping
    public RoomImage create(@RequestBody RoomImage roomImage) {
        return roomImageService.create(roomImage);
    }

    @PutMapping
    public RoomImage update(@RequestBody RoomImage roomImage) {
        return roomImageService.update(roomImage);
    }
}

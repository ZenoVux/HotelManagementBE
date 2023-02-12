package com.devz.hotelmanagement.rest.controllers;


import com.devz.hotelmanagement.entities.RoomDetail;
import com.devz.hotelmanagement.services.RoomDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/room-details")
public class RoomDetailRestController {

    @Autowired
    private RoomDetailService roomDetailService;

    @GetMapping
    public List<RoomDetail> getAll() {
        return roomDetailService.findAll();
    }

    @PostMapping
    public RoomDetail create(@RequestBody RoomDetail roomDetail) {
        return roomDetailService.create(roomDetail);
    }

    @PutMapping
    public RoomDetail update(@RequestBody RoomDetail roomDetail) {
        return roomDetailService.update(roomDetail);
    }
}

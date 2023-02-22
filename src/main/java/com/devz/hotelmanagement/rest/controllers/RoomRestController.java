package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.Room;
import com.devz.hotelmanagement.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/rooms")
public class RoomRestController {
    @Autowired
    private RoomService roomService;

    @GetMapping
    public List<Room> getAll() {
        return roomService.findAll();
    }

    @GetMapping("/{id}")
    public Room getOne(@PathVariable("id") Integer id) {
        return roomService.findById(id);
    }

    @PostMapping
    public Room create(@RequestBody Room room) {
        return roomService.create(room);
    }

    @PutMapping
    public Room update(@RequestBody Room room) {
        return roomService.update(room);
    }
}

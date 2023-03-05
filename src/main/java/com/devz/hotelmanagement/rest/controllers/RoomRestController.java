package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.Room;
import com.devz.hotelmanagement.models.RoomStatusCount;
import com.devz.hotelmanagement.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Room> getOne(@PathVariable("id") Integer id) {
        Room room = roomService.findById(id);
        if (room != null) {
            return ResponseEntity.ok(room);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("code-room/{code}")
    public Room getByCode(@PathVariable("code") String code) {
        return roomService.findByCode(code);
    }

    @PostMapping
    public Room create(@RequestBody Room room) {
        return roomService.create(room);
    }

    @PutMapping
    public Room update(@RequestBody Room room) {
        return roomService.update(room);
    }

    @GetMapping("/status-count")
    public List<RoomStatusCount> getStatusCount() {
        return roomService.getStatusCount();
    }

    @GetMapping("/floor/{id}")
    public ResponseEntity<List<Room>> getByFlooId(@PathVariable("id") Optional<Integer> id) {
        if (id.isPresent()) {
            return ResponseEntity.ok(roomService.getByFloorId(id.get()));
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}

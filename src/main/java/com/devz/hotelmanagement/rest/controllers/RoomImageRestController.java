package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.RoomTypeImage;
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
    public List<RoomTypeImage> getAll() {
        return roomImageService.findAll();
    }
    
    @GetMapping("/{codeRoom}")
    public List<RoomTypeImage> getByCodeRoom(@PathVariable("codeRoom") String codeRoom) {
        return roomImageService.getListByCodeRoom(codeRoom);
    }
    @PostMapping
    public RoomTypeImage create(@RequestBody RoomTypeImage roomTypeImage) {
        return roomImageService.create(roomTypeImage);
    }

    @PutMapping
    public RoomTypeImage update(@RequestBody RoomTypeImage roomTypeImage) {
        return roomImageService.update(roomTypeImage);
    }
    
    @DeleteMapping("/{id}")
    public void deleteByID(@PathVariable("id") Integer id) {
        roomImageService.deleteById(id);
    }
}

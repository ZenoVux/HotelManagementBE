package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.RoomTypeImage;
import com.devz.hotelmanagement.services.RoomTypeImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/room-type-images")
public class RoomTypeImageRestController {

    @Autowired
    private RoomTypeImageService roomTypeImageService;

    @GetMapping
    public List<RoomTypeImage> getAll() {
        return roomTypeImageService.findAll();
    }
    
//    @GetMapping("/{codeRoom}")
//    public List<RoomTypeImage> getByCodeRoom(@PathVariable("codeRoom") String codeRoom) {
//        return roomTypeImageService.getListByCodeRoom(codeRoom);
//    }

    @PostMapping
    public RoomTypeImage create(@RequestBody RoomTypeImage roomTypeImage) {
        return roomTypeImageService.create(roomTypeImage);
    }

    @PutMapping
    public RoomTypeImage update(@RequestBody RoomTypeImage roomTypeImage) {
        return roomTypeImageService.update(roomTypeImage);
    }
    
    @DeleteMapping("/{id}")
    public void deleteByID(@PathVariable("id") Integer id) {
        roomTypeImageService.deleteById(id);
    }
}

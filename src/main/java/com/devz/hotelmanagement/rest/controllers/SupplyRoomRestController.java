package com.devz.hotelmanagement.rest.controllers;


import com.devz.hotelmanagement.entities.SupplyRoomType;
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
    public List<SupplyRoomType> getAll() {
        return supplyRoomService.findAll();
    }
    
    @GetMapping("/{code}")
    public List<SupplyRoomType> getByCodeRoom(@PathVariable("code") String code) {
        return supplyRoomService.findByCodeRoom(code);
    }
    
    @PostMapping
    public SupplyRoomType create(@RequestBody SupplyRoomType supplyRoomType) {
        return supplyRoomService.create(supplyRoomType);
    }

    @PutMapping
    public SupplyRoomType update(@RequestBody SupplyRoomType supplyRoomType) {
        return supplyRoomService.update(supplyRoomType);
    }
    
    @DeleteMapping("{id}")
    public void deleteByID(@PathVariable("id") Integer id) {
    	supplyRoomService.deleteById(id);
    }
}

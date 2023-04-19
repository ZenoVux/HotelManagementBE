package com.devz.hotelmanagement.rest.controllers;


import com.devz.hotelmanagement.entities.SupplyRoomType;
import com.devz.hotelmanagement.services.SupplyRoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/supply-room-types")
public class SupplyRoomTypeRestController {

    @Autowired
    private SupplyRoomTypeService supplyRoomTypeService;

    @GetMapping
    public List<SupplyRoomType> getAll() {
        return supplyRoomTypeService.findAll();
    }
    
//    @GetMapping("/{code}")
//    public List<SupplyRoomType> getByCodeRoom(@PathVariable("code") String code) {
//        return supplyRoomTypeService.findByCodeRoom(code);
//    }
    
    @PostMapping
    public SupplyRoomType create(@RequestBody SupplyRoomType supplyRoomType) {
        return supplyRoomTypeService.create(supplyRoomType);
    }

    @PutMapping
    public SupplyRoomType update(@RequestBody SupplyRoomType supplyRoomType) {
        return supplyRoomTypeService.update(supplyRoomType);
    }
    
    @DeleteMapping("{id}")
    public void deleteByID(@PathVariable("id") Integer id) {
    	supplyRoomTypeService.deleteById(id);
    }
}

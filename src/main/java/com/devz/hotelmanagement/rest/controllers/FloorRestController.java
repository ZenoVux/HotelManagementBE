package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.Floor;
import com.devz.hotelmanagement.services.FloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/floors")
public class FloorRestController {

    @Autowired
    private FloorService floorService;

    @GetMapping
    public List<Floor> getAll() {
        return floorService.findAll();
    }

    @PostMapping
    public Floor create(@RequestBody Floor floor) {
        return floorService.create(floor);
    }

    @PutMapping
    public Floor update(@RequestBody Floor floor) {
        return floorService.update(floor);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id){
        floorService.delete(id);
    }
}

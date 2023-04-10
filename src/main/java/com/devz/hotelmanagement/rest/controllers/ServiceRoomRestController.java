package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.ServiceRoom;
import com.devz.hotelmanagement.services.ServiceRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/services")
public class ServiceRoomRestController {
    @Autowired
    private ServiceRoomService serviceRoomService;

    @GetMapping
    public List<ServiceRoom> findAll(@RequestParam("status") Optional<Boolean> status){
        if (status.isPresent()) {
            return serviceRoomService.findByStatus(status.get());
        } else {
            return serviceRoomService.findAll();
        }
    }

    @PostMapping
    public ServiceRoom create(@RequestBody ServiceRoom serviceRoom){
        return serviceRoomService.create(serviceRoom);
    }

    @PutMapping
    public ServiceRoom update(@RequestBody ServiceRoom serviceRoom){
        return serviceRoomService.update(serviceRoom);
    }
}

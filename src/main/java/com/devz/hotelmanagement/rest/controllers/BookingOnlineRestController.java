package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.RoomType;
import com.devz.hotelmanagement.services.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/booking-online")
public class BookingOnlineRestController {

    @Autowired
    private RoomTypeService roomTypeService;

    @GetMapping("/room-types")
    public List<RoomType> findAllRoomType() {
        return roomTypeService.findAll();
    }

    @GetMapping("/room-types/{code}")
    public RoomType findRoomTypeByCode(@PathVariable("code") String code) {
        return roomTypeService.findByCode(code);
    }

}

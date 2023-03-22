package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.models.Hotel;
import com.devz.hotelmanagement.models.HotelRoom2;
import com.devz.hotelmanagement.services.HotelRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/hotel")
public class HotelRoomController {

    @Autowired
    private HotelRoomService hotelRoomService;

    @GetMapping
    public Hotel getHotel() {
        return hotelRoomService.getHotel();
    }

}

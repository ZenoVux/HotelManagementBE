package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.*;
import com.devz.hotelmanagement.models.RoomBooking;
import com.devz.hotelmanagement.services.BedRoomService;
import com.devz.hotelmanagement.services.BookingService;
import com.devz.hotelmanagement.services.RoomService;
import com.devz.hotelmanagement.services.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/bookings")
public class BookingRestController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomTypeService roomTypeService;

    @Autowired
    private BedRoomService bedRoomService;

    @GetMapping
    public List<Booking> getAll() {
        return bookingService.findAll();
    }

    @GetMapping("/rooms")
    public List<RoomBooking> getRoomBooking(){
        List<RoomBooking> roomBookings = new ArrayList<>();
        List<RoomType> roomTypes = roomTypeService.findAll();
        List<Room> rooms = roomService.getRoomBookings("standard");
        List<BedRoom> bedRooms = bedRoomService.getBedRoomsByRoomId(44);

        String rbName = "";
        Double rbPrice = 0.0;
        RoomBooking roomBooking = new RoomBooking();
        for (Room room : rooms) {

            rbName = "Phòng " + room.getRoomType().getName()
                    + " | ";
            for (BedRoom bedRoom : bedRooms) {
                rbName = rbName + bedRoom.getQuantityBed() + " " + bedRoom.getBedType().getName() + " ";
                rbPrice = room.getPrice();
            }

        }
        roomBooking.setName(rbName);
        roomBooking.setQuantity(rooms.size());
        roomBooking.setPrice(rbPrice);
        roomBookings.add(roomBooking);
        return roomBookings;
    }

    @PostMapping
    public Booking create(@RequestBody Booking booking) {
        return bookingService.create(booking);
    }

    @PutMapping
    public Booking update(@RequestBody Booking booking) {
        return bookingService.update(booking);
    }
}

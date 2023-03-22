package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.*;
import com.devz.hotelmanagement.models.RoomBooking;
import com.devz.hotelmanagement.services.*;
import com.devz.hotelmanagement.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/bookings")
public class BookingRestController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private BedTypeService bedTypeService;

    @Autowired
    private RoomTypeService roomTypeService;

    @Autowired
    private BedRoomService bedRoomService;

    @GetMapping
    public List<Booking> getAll() {
        return bookingService.findAll();
    }

    @GetMapping("/info")
    public List<RoomBooking> getInfoRoomBooking(
            @RequestParam("checkinDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkinDate,
            @RequestParam("checkoutDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkoutDate,
            @RequestParam("roomType") String roomType
    ){

        if(roomType == "") roomType = null;

        List<RoomBooking> roomTypeBookingList = new ArrayList<>();
        List<Object[]> infoRoomBooking = bookingService.getInfoRoomBooking(roomType,checkinDate,checkoutDate);

        for (Object[] bookingInfo : infoRoomBooking) {
            RoomBooking roomBooking = new RoomBooking();
            roomBooking.setRoomType(roomTypeService.getRoomTypeByCode((String)bookingInfo[2]));
            roomBooking.setName((String)bookingInfo[0]);
            roomBooking.setQuantity((Long)bookingInfo[1]);

            List<Integer> roomIds = bookingService.getRoomsByTimeBooking((String)bookingInfo[0], checkinDate, checkoutDate);
            List<Room> roomList = new ArrayList<>();
            for (Integer roomId : roomIds) {
                Room room = roomService.findById(roomId);
                if (room != null) {
                    roomList.add(room);
                }
            }
            roomBooking.setListRooms(roomList);

            List<Double> roomPrices = new ArrayList<>();
            for (Room room : roomList) {
                Double price = room.getPrice();
                if (price != null) {
                    roomPrices.add(price);
                }
            }
            Double minPrice = roomPrices.isEmpty() ? 0.0 : Collections.min(roomPrices);
            Double maxPrice = roomPrices.isEmpty() ? 0.0 : Collections.max(roomPrices);
            roomBooking.setMinPrice(minPrice);
            roomBooking.setMaxPrice(maxPrice);
            roomBooking.setMaxAdults((BigDecimal)bookingInfo[3]);
            roomBooking.setMaxChilds((BigDecimal)bookingInfo[4]);
            roomTypeBookingList.add(roomBooking);
        }

        return roomTypeBookingList;
    }

    @PostMapping
    public Booking create(@RequestBody Booking booking) {
        return bookingService.create(booking);
    }

    @PutMapping
    public Booking update(@RequestBody Booking booking) {
        return bookingService.update(booking);
    }

    @GetMapping("/invoice-code/{code}")
    public ResponseEntity<Booking> findByInvoiceCode(@PathVariable("code") String code){
        Booking booking = bookingService.findByInvoiceCode(code);
        if (booking != null) {
            return ResponseEntity.ok(booking);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}

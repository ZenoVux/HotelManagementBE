package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.BookingDetailHistory;
import com.devz.hotelmanagement.services.BookingDetailHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/booking-detail-histories")
public class BookingDetailHistoryRestController {

    @Autowired
    private BookingDetailHistoryService bookingDetailHistoryService;

    @GetMapping
    public List<BookingDetailHistory> getAll() {
        return bookingDetailHistoryService.findAll();
    }

    @PostMapping
    public BookingDetailHistory create(@RequestBody BookingDetailHistory bookingDetailHistory) {
        return bookingDetailHistoryService.create(bookingDetailHistory);
    }

    @PutMapping
    public BookingDetailHistory update(@RequestBody BookingDetailHistory bookingDetailHistory) {
        return bookingDetailHistoryService.update(bookingDetailHistory);
    }

}

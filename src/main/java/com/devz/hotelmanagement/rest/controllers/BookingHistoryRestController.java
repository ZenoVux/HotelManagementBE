package com.devz.hotelmanagement.rest.controllers;


import com.devz.hotelmanagement.entities.Booking;
import com.devz.hotelmanagement.entities.BookingDetailHistory;
import com.devz.hotelmanagement.entities.BookingHistory;
import com.devz.hotelmanagement.models.BookingHistoryInfo;
import com.devz.hotelmanagement.services.BookingDetailHistoryService;
import com.devz.hotelmanagement.services.BookingHistoryService;
import com.devz.hotelmanagement.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/booking-histories")
public class BookingHistoryRestController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingHistoryService bookingHistoryService;

    @Autowired
    private BookingDetailHistoryService bookingDetailHistoryService;

    @GetMapping("/{code}")
    public List<BookingHistoryInfo> getBookingHistoryInfo(@PathVariable("code") String code) {
        List<BookingHistoryInfo> bookingHistoryInfos = new ArrayList<>();
        Booking booking = bookingService.findByCode(code);
        List<BookingHistory> bookingHistories = bookingHistoryService.findByBookingId(booking.getId());
        for (BookingHistory bookingHistory : bookingHistories) {
            List<BookingDetailHistory> bookingDetailHistories = bookingDetailHistoryService.findByBookingHistoryId(bookingHistory.getId());
            bookingHistoryInfos.add(new BookingHistoryInfo(bookingHistory, bookingDetailHistories));
        }
        return bookingHistoryInfos;
    }

    @PostMapping
    public BookingHistory create(@RequestBody BookingHistory bookingDetailHistory) {
        return bookingHistoryService.create(bookingDetailHistory);
    }

    @PutMapping
    public BookingHistory update(@RequestBody BookingHistory bookingDetailHistory) {
        return bookingHistoryService.update(bookingDetailHistory);
    }

}

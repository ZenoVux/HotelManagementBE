package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.BookingDetail;
import com.devz.hotelmanagement.services.BookingDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/booking-details")
public class BookingDetailRestController {

    @Autowired
    private BookingDetailService bookingDetailService;

    @GetMapping
    public List<BookingDetail> getAll() {
        return bookingDetailService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDetail> findByCode(@PathVariable("id") Integer id) {
        try {
            BookingDetail bookingDetail = bookingDetailService.findById(id);
            return ResponseEntity.ok(bookingDetail);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public BookingDetail create(@RequestBody BookingDetail bookingDetail) {
        return bookingDetailService.create(bookingDetail);
    }

    @PutMapping
    public BookingDetail update(@RequestBody BookingDetail bookingDetail) {
        return bookingDetailService.update(bookingDetail);
    }

    @GetMapping("/waiting-checkin/{code}")
    public ResponseEntity<BookingDetail> findWaitingCheckinByRoomCode(@PathVariable("code") String code) {
        BookingDetail bookingDetail = bookingDetailService.findWaitingCheckinByRoomCode(code);
        if (bookingDetail != null) {
            return ResponseEntity.ok(bookingDetail);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/checkout-room/{code}")
    public ResponseEntity<BookingDetail> getByCheckoutRoomCode(@PathVariable("code") String code) {
        BookingDetail bookingDetail = bookingDetailService.findByCheckedinRoomCode(code);
        if (bookingDetail != null) {
            return ResponseEntity.ok(bookingDetail);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}

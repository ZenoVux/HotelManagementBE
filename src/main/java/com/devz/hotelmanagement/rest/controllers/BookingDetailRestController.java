package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.*;
import com.devz.hotelmanagement.models.BookingDetailReq;
import com.devz.hotelmanagement.services.BookingDetailHistoryService;
import com.devz.hotelmanagement.services.BookingDetailService;
import com.devz.hotelmanagement.services.BookingHistoryService;
import com.devz.hotelmanagement.services.BookingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/booking-details")
public class BookingDetailRestController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingHistoryService bookingHistoryService;

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

    @PostMapping("/add-bkd")
    public void addBKD(
            @RequestParam("bookingDetailReq") String bookingDetailReqJson
    ) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setDateFormat(dateFormat);
            BookingDetailReq bookingDetailReq = objectMapper.readValue(bookingDetailReqJson, BookingDetailReq.class);

            Booking booking = bookingService.findByCode(bookingDetailReq.getBookingCode());

            //Đưa bản hiện tại thành history
            bookingHistoryService.updateBeforeEditBooking(booking);

            // Cập nhật booking hiện tại
            booking.setNumAdults(booking.getNumAdults() + bookingDetailReq.getNumAdults());
            booking.setNumChildren(booking.getNumChildren() + bookingDetailReq.getNumChilds());
            booking.setCreatedDate(new Date());

            bookingService.update(booking);

            // Đặt thêm phòng
            List<Room> rooms = List.of(bookingDetailReq.getRooms());
            List<BookingDetail> newBookingDetails = rooms.stream()
                    .map(room -> new BookingDetail(
                            room,
                            bookingDetailReq.getCheckinExpected(),
                            bookingDetailReq.getCheckoutExpected(),
                            booking,
                            room.getPrice(),
                            bookingDetailReq.getNote(),
                            1
                    ))
                    .collect(Collectors.toList());

            bookingDetailService.createAll(newBookingDetails);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

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

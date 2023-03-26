package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.InvoiceDetail;
import com.devz.hotelmanagement.models.CancelRoom;
import com.devz.hotelmanagement.models.CheckinRoomReq;
import com.devz.hotelmanagement.models.CheckoutRoom;
import com.devz.hotelmanagement.models.Hotel;
import com.devz.hotelmanagement.services.HotelRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/checkin")
    public ResponseEntity<InvoiceDetail> checkin(@RequestBody CheckinRoomReq checkinRoomReq) {
        try {
            InvoiceDetail invoiceDetail = hotelRoomService.checkin(checkinRoomReq);
            return ResponseEntity.ok(invoiceDetail);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/cancel")
    public ResponseEntity<Void> cancel(@RequestBody CancelRoom cancelRoom) {
        try {
            hotelRoomService.cancel(cancelRoom.getCode(), cancelRoom.getNote());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity<InvoiceDetail> checkout(@RequestBody CheckoutRoom checkoutRoom) {
        try {
            InvoiceDetail invoiceDetail = hotelRoomService.checkout(checkoutRoom);
            return ResponseEntity.ok(invoiceDetail);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}

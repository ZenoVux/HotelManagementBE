package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.Invoice;
import com.devz.hotelmanagement.entities.InvoiceDetail;
import com.devz.hotelmanagement.entities.Room;
import com.devz.hotelmanagement.models.CancelRoom;
import com.devz.hotelmanagement.models.HotelRoom;
import com.devz.hotelmanagement.models.RoomStatus;
import com.devz.hotelmanagement.models.StatusCount;
import com.devz.hotelmanagement.services.HotelRoomService;
import com.devz.hotelmanagement.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/rooms")
public class RoomRestController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private HotelRoomService hotelRoomService;

    @GetMapping
    public List<Room> getAll() {
        return roomService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getOne(@PathVariable("id") Integer id) {
        Room room = roomService.findById(id);
        if (room != null) {
            return ResponseEntity.ok(room);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("code-room/{code}")
    public Room getByCode(@PathVariable("code") String code) {
        return roomService.findByCode(code);
    }

    @PostMapping
    public Room create(@RequestBody Room room) {
        return roomService.create(room);
    }

    @PutMapping
    public Room update(@RequestBody Room room) {
        return roomService.update(room);
    }

    @PutMapping("/status")
    public void updateStatus(@RequestBody RoomStatus roomStatus) {
        roomService.updateStatus(roomStatus.getCode(), roomStatus.getStatus());
    }

    @GetMapping("/status-count")
    public List<StatusCount> getStatusCount() {
        return roomService.getStatusCount();
    }

    @GetMapping("/hotel-room")
    public List<HotelRoom> getHotelRoom() {
        return roomService.getHotelRoom();
    }

    @GetMapping("/floor/{id}")
    public ResponseEntity<List<Room>> getByFlooId(@PathVariable("id") Optional<Integer> id) {
        if (id.isPresent()) {
            return ResponseEntity.ok(roomService.getByFloorId(id.get()));
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/checkin/{code}")
    public ResponseEntity<InvoiceDetail> checkin(@PathVariable("code") String code) {
        try {
            InvoiceDetail invoiceDetail = hotelRoomService.checkin(code);
            return ResponseEntity.ok(invoiceDetail);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/cancel-room")
    public ResponseEntity<Void> cancel(@RequestBody CancelRoom cancelRoom) {
        try {
            hotelRoomService.cancel(cancelRoom.getCode(), cancelRoom.getNote());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity<Invoice> checkout(@RequestBody String[] codes) {
        try {
            Invoice invoice = hotelRoomService.checkout(codes);
            return ResponseEntity.ok(invoice);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

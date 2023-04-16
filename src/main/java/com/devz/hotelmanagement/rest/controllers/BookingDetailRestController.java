package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.*;
import com.devz.hotelmanagement.models.BookingDetailEdit;
import com.devz.hotelmanagement.models.BookingDetailReq;
import com.devz.hotelmanagement.services.*;
import com.devz.hotelmanagement.statuses.BookingDetailStatus;
import com.devz.hotelmanagement.utilities.CurrentAccount;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/booking-details")
public class BookingDetailRestController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private BookingHistoryService bookingHistoryService;

    @Autowired
    private BookingDetailService bookingDetailService;

    @Autowired
    private AccountService accountService;

    private CurrentAccount currentAccount;

    @GetMapping
    public List<BookingDetail> getAll() {
        return bookingDetailService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDetail> findById(@PathVariable("id") Integer id) {
        try {
            BookingDetail bookingDetail = bookingDetailService.findById(id);
            return ResponseEntity.ok(bookingDetail);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-info/{id}")
    public BookingDetailEdit getInfoBookingDetail(@PathVariable("id") Integer id) {

        List<BookingDetailEdit> bookingDetails = new ArrayList<>();
        List<Object[]> infoList = bookingDetailService.getInfoBookingDetail(id);
        for (Object[] info : infoList) {
            BookingDetailEdit bookingDetail = new BookingDetailEdit((String) info[0], (String) info[1], (String) info[2], (Double) info[3], (Date) info[4], (Date) info[5], (String) info[6]);
            bookingDetails.add(bookingDetail);
        }

        return bookingDetails.get(0);
    }

    @GetMapping("/get-room-by-type")
    public List<Room> getRoomByType(@RequestParam("bookingCode") String bookingCode, @RequestParam("roomCode") String roomCode, @RequestParam("checkin") String checkin, @RequestParam("checkout") String checkout, @RequestParam("roomType") String roomType) {

        try {
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date checkinDate = formatter.parse(checkin);
            Date checkoutDate = formatter.parse(checkout);

            List<Integer> roomIdList = bookingService.getRoomsByTimeBooking(roomType, checkinDate, checkoutDate);
            List<Room> rooms = roomIdList.stream().map(roomId -> roomService.findById(roomId)).collect(Collectors.toList());

            List<BookingDetail> listBkdCheck = bookingDetailService.checkRoomInRangeDay(roomCode, checkinDate, checkoutDate, bookingCode);

            if (listBkdCheck.isEmpty()) {
                Room currentRoom = roomService.findByCode(roomCode);
                rooms.add(currentRoom);
            }

            return rooms;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public BookingDetail create(@RequestBody BookingDetail bookingDetail) {
        return bookingDetailService.create(bookingDetail);
    }

    @PostMapping("/add-bkd")
    public ResponseEntity<?> addBKD(@RequestParam("bookingDetailReq") String bookingDetailReqJson) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setDateFormat(dateFormat);
            BookingDetailReq bookingDetailReq = objectMapper.readValue(bookingDetailReqJson, BookingDetailReq.class);
            Booking booking = bookingService.findByCode(bookingDetailReq.getBookingCode());
            List<Room> rooms = List.of(bookingDetailReq.getRooms());
            boolean areAllRoomsAvailable = true;
            for (Room room : rooms) {
                if (!bookingDetailService.findByRoomCodeAndCheckinAndCheckout(room.getCode(), bookingDetailReq.getCheckinExpected(), bookingDetailReq.getCheckoutExpected()).isEmpty()) {
                    areAllRoomsAvailable = false;
                    break;
                }
            }

            if (!areAllRoomsAvailable) {
                throw new RuntimeException("{\"error\": \"Trong danh sách phòng đã chọn có phòng đã được book, vui lòng chọn phòng lại!\"}");
            } else {

                //Đưa bản hiện tại thành history
                bookingHistoryService.updateBeforeEditBooking(booking);

                // Cập nhật booking hiện tại
                Account account = accountService.findByUsername(currentAccount.getUsername());
                booking.setNumAdults(booking.getNumAdults() + bookingDetailReq.getNumAdults());
                booking.setNumChildren(booking.getNumChildren() + bookingDetailReq.getNumChilds());
                booking.setCreatedDate(new Date());
                booking.setAccount(account);
                bookingService.update(booking);

                List<BookingDetail> newBookingDetails = rooms.stream().map(room -> new BookingDetail(room, bookingDetailReq.getCheckinExpected(), bookingDetailReq.getCheckoutExpected(), booking, room.getPrice(), "Thêm: " + bookingDetailReq.getNote(), BookingDetailStatus.PENDING.getCode(), new Date())).collect(Collectors.toList());
                bookingDetailService.createAll(newBookingDetails);
            }
            return ResponseEntity.ok().body(booking);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PostMapping("/edit-bkd")
    public ResponseEntity<?> editBKD(@RequestParam("bookingDetailReq") String bookingDetailReqJson) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setDateFormat(dateFormat);

            BookingDetailReq bookingDetailReq = objectMapper.readValue(bookingDetailReqJson, BookingDetailReq.class);
            Booking booking = bookingService.findByCode(bookingDetailReq.getBookingCode());
            BookingDetail currentBookingDetail = bookingDetailService.findByCode(bookingDetailReq.getBookingDetailCode());
            List<Room> rooms = List.of(bookingDetailReq.getRooms());

            boolean areAllRoomsAvailable = true;
            for (Room room : rooms) {
                if (!bookingDetailService.findByRoomCodeAndCheckinAndCheckout(room.getCode(), bookingDetailReq.getCheckinExpected(), bookingDetailReq.getCheckoutExpected()).isEmpty()) {
                    areAllRoomsAvailable = false;
                    break;
                }
            }

            if (!areAllRoomsAvailable) {
                throw new RuntimeException("{\"error\": \"Trong danh sách phòng đã chọn có phòng đã được book, vui lòng chọn phòng lại!\"}");
            } else {

                //Đưa bản hiện tại thành history
                bookingHistoryService.updateBeforeEditBooking(booking);

                // Cập nhật booking hiện tại
                Account account = accountService.findByUsername(currentAccount.getUsername());
                booking.setCreatedDate(new Date());
                booking.setAccount(account);
                bookingService.update(booking);

                // Chỉnh sửa booking
                currentBookingDetail.setRoom(rooms.get(0));
                currentBookingDetail.setCheckinExpected(bookingDetailReq.getCheckinExpected());
                currentBookingDetail.setCheckoutExpected(bookingDetailReq.getCheckoutExpected());
                currentBookingDetail.setNote(bookingDetailReq.getNote());
                bookingDetailService.update(currentBookingDetail);
            }
            return ResponseEntity.ok().body(currentBookingDetail);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PostMapping("/delete-bkd")
    public ResponseEntity<?> deleteBKD(@RequestBody Map<String, Object> data) {

        try {

            Integer id = (Integer) data.get("id");
            String reason = (String) data.get("reason");

            BookingDetail bookingDetail = bookingDetailService.findById(id);

            if (bookingDetail.getStatus() != 1) {
                throw new RuntimeException("{\"error\": \"Phòng này đã được checkin, không thể xoá\"}");
            }

            bookingDetail.setNote("Đã bỏ: " + reason);
            bookingDetailService.update(bookingDetail);
            Booking booking = bookingService.findById(bookingDetail.getBooking().getId());

            List<BookingDetail> bookingDetails = bookingDetailService.findByBookingId(booking.getId());
            if (bookingDetails.size() == 1) {
                throw new RuntimeException("{\"error\": \"Không thể xoá khi chỉ có 1 phòng trong booking\"}");
            }

            //Đưa bản hiện tại thành history
            bookingHistoryService.updateBeforeEditBooking(booking);

            Account account = accountService.findByUsername(currentAccount.getUsername());
            booking.setCreatedDate(new Date());
            booking.setAccount(account);
            bookingService.update(booking);
            bookingDetailService.deleteById(id);

            return ResponseEntity.ok().body(booking);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
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

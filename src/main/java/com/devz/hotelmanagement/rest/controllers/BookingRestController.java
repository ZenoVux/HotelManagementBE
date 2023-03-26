package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.*;
import com.devz.hotelmanagement.models.BookingDetailInfo;
import com.devz.hotelmanagement.models.BookingInfo;
import com.devz.hotelmanagement.models.BookingReq;
import com.devz.hotelmanagement.models.RoomBooking;
import com.devz.hotelmanagement.services.*;
import com.devz.hotelmanagement.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/bookings")
public class BookingRestController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingDetailService bookingDetailService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomTypeService roomTypeService;

    @GetMapping
    public List<BookingInfo> getBooking() {
        List<BookingInfo> bookingList = new ArrayList<>();
        List<Object[]> info = bookingService.getBooking();

        for (Object[] i : info) {
            BookingInfo bookingInfo = new BookingInfo();
            bookingInfo.setCode((String) i[0]);
            bookingInfo.setCheckin((Date) i[1]);
            bookingInfo.setCheckout((Date) i[2]);
            bookingInfo.setNumOfRoom((Long) i[3]);
            bookingInfo.setAdults((BigDecimal) i[4]);
            bookingInfo.setChilds((BigDecimal) i[5]);
            bookingInfo.setStatus((Integer) i[6]);
            bookingList.add(bookingInfo);

        }

        return bookingList;
    }

    @GetMapping("/{code}")
    public BookingDetailInfo getById(@PathVariable("code") String code) {
        Booking booking = bookingService.findByCode(code);
        Customer customer = booking.getCustomer();
        List<Room> roomList = bookingDetailService.findByBookingId(booking.getId())
                .stream()
                .map(BookingDetail::getRoom)
                .collect(Collectors.toList());
        return new BookingDetailInfo(customer, roomList);
    }

    @GetMapping("/info")
    public List<RoomBooking> getInfoRoomBooking(
            @RequestParam("checkinDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkinDate,
            @RequestParam("checkoutDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkoutDate,
            @RequestParam("roomType") String roomType
    ) {

        if (roomType == "") roomType = null;

        List<Object[]> infoRoomBooking = bookingService.getInfoRoomBooking(roomType, checkinDate, checkoutDate);

        return infoRoomBooking.stream().map(bookingInfo -> {
            RoomBooking roomBooking = new RoomBooking();
            roomBooking.setRoomType(roomTypeService.getRoomTypeByCode((String) bookingInfo[2]));
            roomBooking.setName((String) bookingInfo[0]);
            roomBooking.setQuantity((Long) bookingInfo[1]);

            List<Integer> roomIds = bookingService.getRoomsByTimeBooking((String) bookingInfo[0], checkinDate, checkoutDate);
            List<Room> roomList = roomService.findByIds(roomIds);
            roomBooking.setListRooms(roomList);

            DoubleSummaryStatistics priceStats = roomList.stream().map(Room::getPrice).filter(Objects::nonNull).mapToDouble(Double::doubleValue).summaryStatistics();
            roomBooking.setMinPrice(priceStats.getMin());
            roomBooking.setMaxPrice(priceStats.getMax());

            roomBooking.setMaxAdults((BigDecimal) bookingInfo[3]);
            roomBooking.setMaxChilds((BigDecimal) bookingInfo[4]);

            return roomBooking;
        }).collect(Collectors.toList());
    }

    @PostMapping
    public void createBooking(@RequestBody BookingReq bookingReq) {
        Booking booking = new Booking();

        String phoneNumber = bookingReq.getCustomer().getPhoneNumber();
        Customer customer = customerService.findByPhoneNumber(phoneNumber);

        if (customer == null) {
            customer = customerService.create(bookingReq.getCustomer());
        }

        booking.setCraetedDate(new Date());
        booking.setCustomer(customer);
        booking.setCheckinExpected(bookingReq.getCheckinExpected());
        booking.setCheckoutExpected(bookingReq.getCheckoutExpected());
        booking.setStatus(1);

        bookingService.create(booking);

        List<Room> rooms = List.of(bookingReq.getRooms());
        List<BookingDetail> bookingDetails = rooms.stream()
                .map(room -> new BookingDetail(room, booking, 0, 0, "", 0, null, null))
                .collect(Collectors.toList());

        bookingDetailService.createAll(bookingDetails);
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

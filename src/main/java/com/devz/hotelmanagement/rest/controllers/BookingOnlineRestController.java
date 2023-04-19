package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.Booking;
import com.devz.hotelmanagement.entities.Promotion;
import com.devz.hotelmanagement.entities.Room;
import com.devz.hotelmanagement.entities.RoomType;
import com.devz.hotelmanagement.models.BookingOnlReq;
import com.devz.hotelmanagement.models.RoomBooking;
import com.devz.hotelmanagement.services.BookingService;
import com.devz.hotelmanagement.services.PromotionService;
import com.devz.hotelmanagement.services.RoomService;
import com.devz.hotelmanagement.services.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/booking-online")
public class BookingOnlineRestController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private RoomTypeService roomTypeService;

    @GetMapping("/room-types")
    public List<RoomType> findAllRoomType() {
        return roomTypeService.findAll();
    }

    @GetMapping("/room-types/{code}")
    public RoomType findRoomTypeByCode(@PathVariable("code") String code) {
        return roomTypeService.findByCode(code);
    }

    @GetMapping("/info")
    public List<RoomBooking> getInfoRoomBooking(@RequestParam("checkinDate") String checkin, @RequestParam("checkoutDate") String checkout, @RequestParam("roomType") String roomType) {

        try {

            if (roomType == "") roomType = null;
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date checkinDate = formatter.parse(checkin);
            Date checkoutDate = formatter.parse(checkout);

            List<Object[]> infoRoomBooking = bookingService.getInfoRoomBooking(roomType, checkinDate, checkoutDate);

            return infoRoomBooking.stream().map(bookingInfo -> {
                RoomBooking roomBooking = new RoomBooking();
                roomBooking.setRoomType(roomTypeService.getRoomTypeByCode((String) bookingInfo[2]));
                roomBooking.setName((String) bookingInfo[0]);
                roomBooking.setQuantity((Long) bookingInfo[1]);

                List<Promotion> promotions = promotionService.findByRoomType((String) bookingInfo[2]);

                if (promotions != null && !promotions.isEmpty()) {
                    roomBooking.setPromotion(promotions.get(0));
                } else {
                    roomBooking.setPromotion(null);
                }

                List<Integer> roomIds = bookingService.getRoomsByTimeBooking((String) bookingInfo[0], checkinDate, checkoutDate);
                List<Room> roomList = roomService.findByIds(roomIds);
                roomBooking.setListRooms(roomList);

                DoubleSummaryStatistics priceStats = roomList.stream().map(Room::getPrice).filter(Objects::nonNull).mapToDouble(Double::doubleValue).summaryStatistics();
                roomBooking.setMinPrice(priceStats.getMin());
                roomBooking.setMaxPrice(priceStats.getMax());

                roomBooking.setMaxAdults((Long) bookingInfo[3]);
                roomBooking.setMaxChilds((Long) bookingInfo[4]);

                return roomBooking;
            }).collect(Collectors.toList());

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/get-booking")
    public Booking createBooking(@RequestBody BookingOnlReq bookingOnlReq){

        Booking booking = new Booking();
        return booking;

    }

}

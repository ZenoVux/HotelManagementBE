package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.*;
import com.devz.hotelmanagement.models.BookingOnlReq;
import com.devz.hotelmanagement.models.RoomBooking;
import com.devz.hotelmanagement.models.RoomBookingOnl;
import com.devz.hotelmanagement.services.*;

import com.devz.hotelmanagement.statuses.BookingStatus;
import com.devz.hotelmanagement.utilities.CurrentAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/booking-online")
public class BookingOnlineRestController {

    @Autowired
    private ServiceRoomService serviceRoomService;

    @Autowired
    private CustomerTypeService customerTypeService;

    @Autowired
    private RoomTypeImageService roomTypeImageService;

    @Autowired
    private RoomTypeService roomTypeService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PaymentMethodService paymentMethodService;

    @Autowired
    private CustomerService customerService;

    private CurrentAccount currentAccount;

    @GetMapping("/room-types")
    public List<RoomType> findAllRoomType() {
        return roomTypeService.findAll();
    }

    @GetMapping("/services")
    public List<ServiceRoom> findAllService() {
        return serviceRoomService.findAll();
    }

    @GetMapping("/promotions")
    public List<Promotion> findAllPomotion() {
        return promotionService.findAll();
    }

    @GetMapping("/rooms")
    public List<Room> findAllRooms() {
        return roomService.findAll();
    }

    @GetMapping("/room-types/{code}")
    public RoomType findRoomTypeByCode(@PathVariable("code") String code) {
        return roomTypeService.findByCode(code);
    }

//	@GetMapping("/img/{codeRoom}")
//	public List<RoomTypeImage> getByCodeRoom(@PathVariable("codeRoom") String codeRoom) {
//		return roomTypeImageService.getListByCodeRoom(codeRoom);
//	}

    @GetMapping("/info")
    public List<RoomBooking> getInfoRoomBooking(@RequestParam("checkinDate") String checkin, @RequestParam("checkoutDate") String checkout, @RequestParam("roomType") String roomType) {

        if (checkin.equals(checkout)) {
            return null;
        }

        try {

            if (roomType == "") roomType = null;
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date checkinDate = formatter.parse(checkin);
            Date checkoutDate = formatter.parse(checkout);

            List<Object[]> infoRoomBooking = bookingService.getInfoRoomBooking(roomType, checkinDate, checkoutDate);

            return infoRoomBooking.stream().map(bookingInfo -> {
                RoomBooking roomBooking = new RoomBooking();
                RoomType type = roomTypeService.getRoomTypeByCode((String) bookingInfo[2]);
                roomBooking.setRoomType(type);
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
                roomBooking.setPrice(type.getPrice());
                roomBooking.setMaxAdults((Long) bookingInfo[3]);
                roomBooking.setMaxChilds((Long) bookingInfo[4]);

                return roomBooking;
            }).collect(Collectors.toList());

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/get-booking")
    public void createBooking(@RequestBody BookingOnlReq bookingData) {
        try {
            Booking booking = new Booking();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date checkinDate = dateFormat.parse(bookingData.getCheckinDate());
            Date checkoutDate = dateFormat.parse(bookingData.getCheckoutDate());

            booking.setCreatedDate(new Date());
            booking.setNumAdults(bookingData.getNumAdults());
            booking.setNumChildren(bookingData.getNumChildren());
            booking.setPaymentMethod(paymentMethodService.findByCode("VNPAY"));
            booking.setNote(bookingData.getNote());
            booking.setStatus(BookingStatus.PENDING.getCode());

            List<RoomBookingOnl> numRoomsBooking = List.of(bookingData.getNumRoomsBooking());

            for (RoomBookingOnl roomBooking : numRoomsBooking) {
                String roomType = roomBooking.getRoomType();
                int numRooms = roomBooking.getNumRooms();
                List<Integer> roomIds = bookingService.getRoomsByTimeBooking(roomType, checkinDate, checkoutDate).subList(0, numRooms);


            }


            Account account = accountService.findByUsername(currentAccount.getUsername());
            booking.setAccount(account);

            Customer customer = new Customer();
            if (customerService.findByPhoneNumber(bookingData.getPhoneNumber()) == null) {
                customer.setFullName(bookingData.getFullName());
                customer.setPhoneNumber(bookingData.getPhoneNumber());
                customer.setEmail(bookingData.getEmail());
                customer.setCustomerType(customerTypeService.findById(1));
                customerService.create(customer);
            }
            booking.setCustomer(customer);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

}

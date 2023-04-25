package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.*;
import com.devz.hotelmanagement.models.*;
import com.devz.hotelmanagement.services.*;

import com.devz.hotelmanagement.statuses.BookingDetailStatus;
import com.devz.hotelmanagement.statuses.BookingStatus;
import com.devz.hotelmanagement.utilities.CurrentAccount;
import com.devz.hotelmanagement.utilities.VNPayUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/booking-online")
public class BookingOnlineRestController {

    @Autowired
    private ServiceRoomService serviceRoomService;

    @Autowired
    private BookingDetailService bookingDetailService;

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

    @Autowired
    private VNPayService vnPayService;

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

	@GetMapping("/img/{codeRoom}")
	public List<RoomTypeImage> getByCodeRoom(@PathVariable("codeRoom") String codeRoom) {
		return roomTypeImageService.getListByCodeRoom(codeRoom);
	}

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

    @PostMapping("/count-down")
    public ResponseEntity<?> countDown(@RequestBody BookingOnlReq bookingData) {
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date checkinDate = dateFormat.parse(bookingData.getCheckinDate());
            Date checkoutDate = dateFormat.parse(bookingData.getCheckoutDate());

            List<RoomBookingOnl> numRoomsBooking = List.of(bookingData.getNumRoomsBooking());
            List<Room> rooms = new ArrayList<>();
            for (RoomBookingOnl roomBooking : numRoomsBooking) {
                String roomType = roomBooking.getRoomType();
                int numRooms = roomBooking.getNumRooms();
                List<Integer> roomIds = bookingService.getRoomsByTimeBooking(roomType, checkinDate, checkoutDate).subList(0, numRooms);
                List<Room> roomsForBooking = roomIds.stream()
                        .map(id -> roomService.findById(id))
                        .collect(Collectors.toList());
                rooms.addAll(roomsForBooking);
            }

            rooms.forEach(room -> room.setStatus(0));
//            roomService.updateAll(rooms);
            return ResponseEntity.ok().body(rooms);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/get-booking")
    public ResponseEntity<?> createBooking(@RequestBody BookingOnlReq bookingData) {
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date checkinDate = dateFormat.parse(bookingData.getCheckinDate());
            Date checkoutDate = dateFormat.parse(bookingData.getCheckoutDate());

            Booking booking = new Booking();
            Account account = accountService.findByUsername("admin");
            booking.setAccount(account);

            Customer customer = new Customer();
            if (customerService.findByPhoneNumber(bookingData.getPhoneNumber()) == null) {
                customer.setFullName(bookingData.getFullName());
                customer.setGender(true);
                customer.setPhoneNumber(bookingData.getPhoneNumber());
                customer.setEmail(bookingData.getEmail());
                customer.setCustomerType(customerTypeService.findById(1));
                customerService.create(customer);
            }else{
                customer = customerService.findByPhoneNumber(bookingData.getPhoneNumber());
            }
            booking.setCustomer(customer);
            booking.setCreatedDate(new Date());
            booking.setNumAdults(bookingData.getNumAdults());
            booking.setNumChildren(bookingData.getNumChildren());
            booking.setPaymentMethod(paymentMethodService.findByCode("VNPAY"));
            booking.setNote(bookingData.getNote());
            booking.setStatus(BookingStatus.PENDING.getCode());

            List<RoomBookingOnl> numRoomsBooking = List.of(bookingData.getNumRoomsBooking());
            List<Room> rooms = new ArrayList<>();
            for (RoomBookingOnl roomBooking : numRoomsBooking) {
                String roomType = roomBooking.getRoomType();
                int numRooms = roomBooking.getNumRooms();
                List<Integer> roomIds = bookingService.getRoomsByTimeBooking(roomType, checkinDate, checkoutDate).subList(0, numRooms);
                List<Room> roomsForBooking = roomIds.stream()
                        .map(id -> roomService.findById(id))
                        .collect(Collectors.toList());
                rooms.addAll(roomsForBooking);
            }

            List<BookingDetail> bookingDetails = rooms.stream().map(room -> {
                room.setStatus(0);
                List<Promotion> promotions = promotionService.findByRoomType(room.getRoomType().getCode());
                if (promotions != null && !promotions.isEmpty()) {
                    double promotionPrice = 0.0;
                    promotionPrice = (100 - promotions.get(0).getPercent()) * room.getRoomType().getPrice() / 100;
                    if ((promotions.get(0).getPercent() * room.getRoomType().getPrice() / 100) > promotions.get(0).getMaxDiscount()) {
                        promotionPrice = room.getRoomType().getPrice() - promotions.get(0).getMaxDiscount();
                    }
                    return new BookingDetail(room, checkinDate, checkoutDate, booking, promotionPrice, "", BookingDetailStatus.CANCELLED.getCode(), new Date());
                }
                return new BookingDetail(room, checkinDate, checkoutDate, booking, room.getRoomType().getPrice(), "", BookingDetailStatus.CANCELLED.getCode(), new Date());
            }).collect(Collectors.toList());

            // update all room
            roomService.updateOrSaveAll(rooms);

            double totalDeposit = 0.0;
            for (BookingDetail bookingDetail : bookingDetails) {
                long diffInMillis = bookingDetail.getCheckoutExpected().getTime() - bookingDetail.getCheckinExpected().getTime();
                long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);
                double deposit = bookingDetail.getRoomPrice() * diffInDays;
                totalDeposit += deposit;
            }
            booking.setDeposit(totalDeposit);

            bookingService.create(booking);

            bookingDetailService.createAll(bookingDetails);

            return ResponseEntity.ok().body(booking);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirm(@RequestParam("bookingConfirmJson") String bookingConfirmJson) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            BookingConfirmReq bookingConfirmReq = objectMapper.readValue(bookingConfirmJson, BookingConfirmReq.class);

            System.out.println(bookingConfirmReq);

            Account account = accountService.findByUsername(currentAccount.getUsername());
            Booking booking = bookingService.findByCode(bookingConfirmReq.getBookingCode());
            booking.setAccount(account);
            booking.setStatus(BookingStatus.CONFIRMED.getCode());
            booking.setNote(bookingConfirmReq.getNote());

            List<BookingDetail> bookingDetails = bookingDetailService.findByBookingId(booking.getId());
            List<Room> rooms = List.of(bookingConfirmReq.getRooms());

            if (rooms.size() != bookingDetails.size()) {
                throw new RuntimeException("{\"error\": \"Số lượng phòng chọn không đúng với số phòng đã book!\"}");
            }

            for (int i = 0; i < bookingDetails.size(); i++) {
                BookingDetail bookingDetail = bookingDetails.get(i);
                Room room = rooms.get(i);
                bookingDetail.setRoom(room);
                bookingDetail.setStatus(BookingDetailStatus.PENDING.getCode());
            }

            bookingService.update(booking);
            bookingDetailService.updateAll(bookingDetails);

            return ResponseEntity.ok().body(booking);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @GetMapping("/get-booking/{code}")
    public Booking getBookingById(@PathVariable("code") String code) {
        return bookingService.findByCode(code);
    }

    @GetMapping("/get-booking-detail/{id}")
    public List<BookingDetail> getBookingDetailByBookingId(@PathVariable("id") Integer id) {
        return bookingDetailService.findByBookingId(id);
    }

    @PostMapping("/cancel-booking/{id}")
    public Booking cancelBookingById(@PathVariable("id") Integer id) {
       Booking bk = new Booking();
       bk = bookingService.findById(id);
       bk.setStatus(BookingStatus.CANCELLED.getCode());
       bookingService.update(bk);
       return bk;

    }

}

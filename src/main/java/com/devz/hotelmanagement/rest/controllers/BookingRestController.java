package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.*;
import com.devz.hotelmanagement.models.*;
import com.devz.hotelmanagement.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
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

    @Autowired
    private PaymentMethodService paymentMethodService;

    @Autowired
    private CustomerTypeService customerTypeService;

    @GetMapping
    public List<BookingInfo> getBooking() {
        List<BookingInfo> bookingList = new ArrayList<>();
        List<Object[]> info = bookingService.getBooking();

        for (Object[] i : info) {
            BookingInfo bookingInfo = new BookingInfo();
            bookingInfo.setCode((String) i[0]);
            bookingInfo.setNumOfRoom((Long) i[1]);
            bookingInfo.setAdults((Integer) i[2]);
            bookingInfo.setChilds((Integer) i[3]);
            bookingInfo.setNote((String) i[4]);
            bookingInfo.setCreatedDate((Date) i[5]);
            bookingInfo.setStatus((Integer) i[6]);
            bookingList.add(bookingInfo);
        }

        return bookingList;
    }

    @GetMapping("/{code}")
    public BookingDetailInfo getById(@PathVariable("code") String code) {
        Booking booking = bookingService.findByCode(code);
        Customer customer = booking.getCustomer();
        List<BookingDetail> bkList = bookingDetailService.findByBookingId(booking.getId());
        return new BookingDetailInfo(code, customer, bkList);
    }

    @GetMapping("/info")
    public List<RoomBooking> getInfoRoomBooking(@RequestParam("checkinDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkinDate, @RequestParam("checkoutDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkoutDate, @RequestParam("roomType") String roomType) {

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
        List<Room> rooms = List.of(bookingReq.getRooms());
        String peopelId = bookingReq.getCustomer().getPeopleId();
        Customer customer = customerService.searchByPeopleId(peopelId);

        if (customer == null) {
            bookingReq.getCustomer().setCustomerType(customerTypeService.findById(1));
            customer = customerService.create(bookingReq.getCustomer());

//            Image frontIdCardImage = new Image(bookingReq.getFrontIdCard());
//            imageService.create(frontIdCardImage);
//
//            Image backIdCardImage = new Image(bookingReq.getBackIdCard());
//            imageService.create(backIdCardImage);
//
//            CustomerImage customerImage = new CustomerImage();
//            customerImage.setCustomer(customer);
        }

        booking.setCraetedDate(new Date());
        booking.setCustomer(customer);
        booking.setNumAdults(bookingReq.getNumAdults());
        booking.setNumChildren(bookingReq.getNumChildren());
        booking.setPaymentMethod(paymentMethodService.findByCode(bookingReq.getPaymentCode()));
        double deposit = rooms.stream().mapToDouble(room -> room.getPrice() * 0.1).sum();
        booking.setDeposit(deposit);
        booking.setNote(bookingReq.getNote());
        booking.setStatus(2);

        bookingService.create(booking);

        List<BookingDetail> bookingDetails = rooms.stream().map(room -> new BookingDetail(room, bookingReq.getCheckinExpected(), bookingReq.getCheckoutExpected(), booking, room.getPrice(), "", 1)).collect(Collectors.toList());

        bookingDetailService.createAll(bookingDetails);
    }

    @PutMapping
    public Booking update(@RequestBody Booking booking) {
        return bookingService.update(booking);
    }

    @PutMapping("/cancel")
    public Booking cancelBooking(@RequestBody Booking booking) {
        Booking cancelBooking = bookingService.findByCode(booking.getCode());
        cancelBooking.setNote(booking.getNote());
        cancelBooking.setStatus(0);
        List<BookingDetail> bookingDetails = bookingDetailService.findByBookingId(cancelBooking.getId());
        bookingDetails.stream().forEach(detail -> detail.setStatus(0));
        return bookingService.update(cancelBooking);
    }

    @GetMapping("/invoice-code/{code}")
    public ResponseEntity<Booking> findByInvoiceCode(@PathVariable("code") String code) {
        Booking booking = bookingService.findByInvoiceCode(code);
        if (booking != null) {
            return ResponseEntity.ok(booking);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/read-front-id-card")
    public ApiFrontIdCardResponse readFrontIdCard(@RequestParam("image") MultipartFile image) {
        ApiFrontIdCardResponse apiFrontIdCardResponse = null;
        try {
            String url = "https://api.fpt.ai/vision/idr/vnm";
            String apiKey = "7p3xRiGSJxqquCyNuk7BQynmGAKsUu2e";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.set("api-key", apiKey);
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("image", new ByteArrayResource(image.getBytes()) {
                @Override
                public String getFilename() {
                    return image.getOriginalFilename();
                }
            });
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<ApiFrontIdCardResponse> responseEntity = restTemplate.postForEntity(url, requestEntity, ApiFrontIdCardResponse.class);
            apiFrontIdCardResponse = responseEntity.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apiFrontIdCardResponse;
    }

    @PostMapping("/read-back-id-card")
    public ApiBackIdCardResponse readBackIdCard(@RequestParam("image") MultipartFile image) {
        try {
            String url = "https://api.fpt.ai/vision/idr/vnm";
            String apiKey = "7p3xRiGSJxqquCyNuk7BQynmGAKsUu2e";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.set("api-key", apiKey);
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("image", new ByteArrayResource(image.getBytes()) {
                @Override
                public String getFilename() {
                    return image.getOriginalFilename();
                }
            });
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<ApiBackIdCardResponse> responseEntity = restTemplate.postForEntity(url, requestEntity, ApiBackIdCardResponse.class);
            ApiBackIdCardResponse apiBackIdCardResponse = responseEntity.getBody();
            return apiBackIdCardResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

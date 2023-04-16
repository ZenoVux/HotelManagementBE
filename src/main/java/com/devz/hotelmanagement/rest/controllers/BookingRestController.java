package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.*;
import com.devz.hotelmanagement.models.*;
import com.devz.hotelmanagement.services.*;
import com.devz.hotelmanagement.statuses.BookingDetailStatus;
import com.devz.hotelmanagement.statuses.BookingStatus;
import com.devz.hotelmanagement.utilities.CurrentAccount;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private CustomerTypeService customerTypeService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private AccountService accountService;

    private CurrentAccount currentAccount;

    @GetMapping
    public List<BookingInfo> getBooking() {
        List<BookingInfo> bookingList = new ArrayList<>();
        List<Object[]> info = bookingService.getBooking();

        for (Object[] i : info) {
            BookingInfo bookingInfo = new BookingInfo((String) i[0], (Long) i[1], (Integer) i[2], (Integer) i[3], (String) i[4], (Date) i[5], (Integer) i[6], (Double) i[7]);
            bookingList.add(bookingInfo);
        }

        return bookingList;
    }

    @GetMapping("/get-by-id/{id}")
    public Booking getById(@PathVariable("id") Integer id) {
        return bookingService.findById(id);
    }

    @GetMapping("/{code}")
    public BookingDetailInfo getByCode(@PathVariable("code") String code) {
        Booking booking = bookingService.findByCode(code);
        Customer customer = booking.getCustomer();
        List<BookingDetail> bkList = bookingDetailService.findByBookingId(booking.getId());
        String username = booking.getAccount().getUsername();
        return new BookingDetailInfo(code, customer, bkList, username);
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
                roomBooking.setRoomType(roomTypeService.getRoomTypeByCode((String) bookingInfo[2]));
                roomBooking.setName((String) bookingInfo[0]);
                roomBooking.setQuantity((Long) bookingInfo[1]);

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

    @PostMapping
    public ResponseEntity<?>  createBooking(
            @RequestParam("frontIdCard") MultipartFile frontIdCard,
            @RequestParam("backIdCard") MultipartFile backIdCard,
            @RequestParam("bookingReq") String bookingReqJson
    ) {

        try {

            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setDateFormat(dateFormat);
            BookingReq bookingReq = objectMapper.readValue(bookingReqJson, BookingReq.class);

            List<Room> rooms = List.of(bookingReq.getRooms());
            boolean areAllRoomsAvailable = true;
            for (Room room : rooms) {
                if(!bookingDetailService.findByRoomCodeAndCheckinAndCheckout(room.getCode(), bookingReq.getCheckinExpected(), bookingReq.getCheckoutExpected()).isEmpty()){
                    areAllRoomsAvailable = false;
                    break;
                }
            }

            if (!areAllRoomsAvailable) {
                throw new RuntimeException("{\"error\": \"Trong danh sách phòng đã chọn có phòng đã được book, vui lòng chọn phòng lại!\"}");
            } else {

                String peopelId = bookingReq.getCustomer().getPeopleId();

                Customer customer = customerService.searchByPeopleId(peopelId);

                if (customer == null) {
                    bookingReq.getCustomer().setCustomerType(customerTypeService.findById(1));
                    bookingReq.getCustomer().setFrontIdCard(storageService.saveFile(frontIdCard));
                    bookingReq.getCustomer().setBackIdCard(storageService.saveFile(backIdCard));
                    customer = customerService.create(bookingReq.getCustomer());
                }

                Account account = accountService.findByUsername(currentAccount.getUsername());

                if(bookingReq.getNote() == null){
                    bookingReq.setNote("");
                }
                Booking booking = new Booking(account, customer, new Date(), bookingReq.getNumAdults(), bookingReq.getNumChildren(), 0.0, null, bookingReq.getNote(), BookingStatus.CONFIRMED.getCode(), null, null);
                bookingService.create(booking);

                List<BookingDetail> bookingDetails = rooms.stream().map(room -> new BookingDetail(room, bookingReq.getCheckinExpected(), bookingReq.getCheckoutExpected(), booking, room.getPrice(), "", BookingDetailStatus.PENDING.getCode(), new Date())).collect(Collectors.toList());
                bookingDetailService.createAll(bookingDetails);

                return ResponseEntity.ok().body(booking);
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PutMapping
    public Booking update(@RequestBody Booking booking) {
        return bookingService.update(booking);
    }

    @PutMapping("/cancel")
    public Booking cancelBooking(@RequestBody Booking booking) {
        Booking cancelBooking = bookingService.findByCode(booking.getCode());
        cancelBooking.setNote(booking.getNote() + " - (Người huỷ: " + currentAccount.getUsername() + ")");
        cancelBooking.setStatus(BookingStatus.CANCELLED.getCode());
        List<BookingDetail> bookingDetails = bookingDetailService.findByBookingId(cancelBooking.getId());
        bookingDetails.stream().forEach(detail -> detail.setStatus(BookingDetailStatus.CANCELLED.getCode()));
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
    public ApiFrontIdCardResponse readFrontIdCard(@RequestParam(name = "frontIdCard") MultipartFile image) {
        return getApiResponse(image, ApiFrontIdCardResponse.class);
    }

    @PostMapping("/read-back-id-card")
    public ApiBackIdCardResponse readBackIdCard(@RequestParam(name = "backIdCard") MultipartFile image) {
        return getApiResponse(image, ApiBackIdCardResponse.class);
    }

    private <T> T getApiResponse(MultipartFile image, Class<T> responseType) {
        try {
            String url = "https://api.fpt.ai/vision/idr/vnm";
            String apiKey = "FkJdbhOQ3Gqq6u6mUfJEmMwaU56QfsIo";
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
            ResponseEntity<T> responseEntity = restTemplate.postForEntity(url, requestEntity, responseType);
            return responseEntity.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

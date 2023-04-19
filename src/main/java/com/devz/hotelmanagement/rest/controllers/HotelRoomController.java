package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.Booking;
import com.devz.hotelmanagement.entities.Customer;
import com.devz.hotelmanagement.entities.Invoice;
import com.devz.hotelmanagement.models.*;
import com.devz.hotelmanagement.services.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/hotel")
public class HotelRoomController {

    @Autowired
    private HotelRoomService hotelRoomService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerTypeService customerTypeService;

    @Autowired
    private StorageService storageService;

    @GetMapping
    public HotelResp getHotel() {
        return hotelRoomService.getHotel();
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> findHotelRoom(@PathVariable("code") Optional<String> code) {
        if(!code.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Dữ liệu không hợp lệ!\"}");
        }
        HotelRoomResp hotelRoomResp = hotelRoomService.getHotelRoom(code.get());
        if (hotelRoomResp != null) {
            return ResponseEntity.status(HttpStatus.OK).body(hotelRoomResp);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Booking không tồn tại!\"}");
        }
    }

    @GetMapping("/booking/{code}")
    public ResponseEntity<?> findBookingByCode(@PathVariable("code") Optional<String> code) {
        if(!code.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Dữ liệu không hợp lệ!\"}");
        }
        Booking booking = bookingService.findByInvoiceCode(code.get());
        if (booking != null) {
            return ResponseEntity.status(HttpStatus.OK).body(booking);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Booking không tồn tại!\"}");
        }
    }

    @GetMapping("/check-extend-checkout-date")
    public ResponseEntity<?> checkExtendCheckoutDate(
            @RequestParam("code") Optional<String> code,
            @RequestParam("checkoutDate")  @DateTimeFormat(pattern = "dd/MM/yyyy") Optional<Date> checkoutDate) {
        if(!code.isPresent() || !checkoutDate.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Dữ liệu không hợp lệ!\"}");
        }
        try {
            hotelRoomService.checkExtendCheckoutDate(code.get(), checkoutDate.get());
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/checkin")
    public ResponseEntity<?> checkin(@RequestBody CheckinRoomReq checkinRoomReq) {
        try {
            hotelRoomService.checkin(checkinRoomReq);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancel(@RequestBody CancelRoomReq cancelRoomReq) {
        try {
            hotelRoomService.cancel(cancelRoomReq.getCode(), cancelRoomReq.getNote());
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestBody CheckoutRoomReq checkoutRoomReq) {
        try {
            hotelRoomService.checkout(checkoutRoomReq);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/extend-checkout-date")
    public ResponseEntity<?> extendCheckoutDate(@RequestBody ExtendCheckoutDateRoomReq extendCheckoutDateRoomReq) {
        try {
            hotelRoomService.extendCheckoutDate(extendCheckoutDateRoomReq.getCode(), extendCheckoutDateRoomReq.getExtendDate(), extendCheckoutDateRoomReq.getNote());
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/ready")
    public ResponseEntity<?> ready(@RequestBody ReadyRoomReq readyRoomReq) {
        try {
            hotelRoomService.ready(readyRoomReq.getCode());
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/change")
    public ResponseEntity<?> change(@RequestBody ChangeRoomReq changeRoomReq) {
        try {
            hotelRoomService.change(changeRoomReq.getFromRoomCode(), changeRoomReq.getToRoomCode(), changeRoomReq.getCheckoutDate(), changeRoomReq.getNote());
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/payment")
    public ResponseEntity<?> payment(@RequestBody PaymentInvoiceReq paymentInvoiceReq) {
        try {
            System.out.println(paymentInvoiceReq);
            hotelRoomService.payment(paymentInvoiceReq.getInvoiceCode(), paymentInvoiceReq.getPromotionCode(), paymentInvoiceReq.getPaymentMethodCode(), paymentInvoiceReq.getNote());
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/confirm-payment")
    public ResponseEntity<?> confirmPayment(@RequestBody PaymentInvoiceReq paymentInvoiceReq) {
        try {
            System.out.println(paymentInvoiceReq);
            hotelRoomService.confirmPayment(paymentInvoiceReq.getInvoiceCode());
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/update-invoice-detail")
    public ResponseEntity<?> updateInvoiceDetail(@RequestBody InvoiceDetailUpdateReq invoiceDetailUpdateReq) {
        try {
            hotelRoomService.updateInvoiceDetail(invoiceDetailUpdateReq);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/split-invoice")
    public ResponseEntity<?> splitInvoice(@RequestBody InvoiceSplitReq invoiceSplitReq) {
        try {
            Invoice invoice = hotelRoomService.splitInvoice(invoiceSplitReq);
            return ResponseEntity.status(HttpStatus.OK).body("{\"code\":\"" + invoice.getCode() + "\"}");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/create-customer")
    public ResponseEntity<?> updateInvoiceDetail(
            @RequestParam("frontIdCard") MultipartFile frontIdCard,
            @RequestParam("backIdCard") MultipartFile backIdCard,
            @RequestParam("customer") String customerReqJson
    ) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
            Customer customerReq = objectMapper.readValue(customerReqJson, Customer.class);

            Customer customer = customerService.searchByPeopleId(customerReq.getPeopleId());

            if (customer == null) {
                customerReq.setCustomerType(customerTypeService.findById(1));
                customerReq.setFrontIdCard(storageService.saveFile(frontIdCard));
                customerReq.setBackIdCard(storageService.saveFile(backIdCard));
                if (customerService.create(customerReq) == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
                }
            } else {
                customer.setEmail(customerReq.getEmail());
                customer.setPhoneNumber(customerReq.getPhoneNumber());
                if (customerService.update(customer) == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
                }
            }
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
    }

}

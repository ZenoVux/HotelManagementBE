package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.Invoice;
import com.devz.hotelmanagement.entities.InvoiceDetail;
import com.devz.hotelmanagement.entities.UsedService;
import com.devz.hotelmanagement.models.*;
import com.devz.hotelmanagement.services.HotelRoomService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public HotelResp getHotel() {
        return hotelRoomService.getHotel();
    }

    @PostMapping("/checkin")
    public ResponseEntity<Void> checkin(@RequestBody CheckinRoomReq checkinRoomReq) {
        try {
            hotelRoomService.checkin(checkinRoomReq);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/cancel")
    public ResponseEntity<Void> cancel(@RequestBody CancelRoomReq cancelRoomReq) {
        try {
            hotelRoomService.cancel(cancelRoomReq.getCode(), cancelRoomReq.getNote());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity<Void> checkout(@RequestBody CheckoutRoomReq checkoutRoomReq) {
        try {
            hotelRoomService.checkout(checkoutRoomReq);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/extend-checkout-date")
    public ResponseEntity<InvoiceDetail> extendCheckoutDate(@RequestBody ExtendCheckoutDateRoomReq extendCheckoutDateRoomReq) {
        try {
            InvoiceDetail invoiceDetail = hotelRoomService.extendCheckoutDate(extendCheckoutDateRoomReq.getCode(), extendCheckoutDateRoomReq.getExtendDate(), extendCheckoutDateRoomReq.getNote());
            return ResponseEntity.ok(invoiceDetail);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/used-service")
    public ResponseEntity<UsedService> usedService(@RequestBody UsedServiceReq usedServiceReq) {
        try {
            UsedService usedService = hotelRoomService.usedService(usedServiceReq.getInvoiceDetailId(), usedServiceReq.getServiceId(), usedServiceReq.getQuantity());
            return ResponseEntity.ok(usedService);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/ready")
    public ResponseEntity<Void> ready(@RequestBody ReadyRoomReq readyRoomReq) {
        try {
            hotelRoomService.ready(readyRoomReq.getCode());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/change")
    public ResponseEntity<Void> change(@RequestBody ChangeRoomReq changeRoomReq) {
        try {
            hotelRoomService.change(changeRoomReq.getFromRoomCode(), changeRoomReq.getToRoomCode(), changeRoomReq.getNote());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/payment")
    public ResponseEntity<Void> payment(@RequestBody PaymentInvoiceReq paymentInvoiceReq) {
        try {
            System.out.println(paymentInvoiceReq);
            hotelRoomService.payment(paymentInvoiceReq.getInvoiceCode(), paymentInvoiceReq.getPromotionCode(), paymentInvoiceReq.getPaymentMethodCode());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/confirm-payment")
    public ResponseEntity<Void> confirmPayment(@RequestBody PaymentInvoiceReq paymentInvoiceReq) {
        try {
            System.out.println(paymentInvoiceReq);
            hotelRoomService.confirmPayment(paymentInvoiceReq.getInvoiceCode());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}

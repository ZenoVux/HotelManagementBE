package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.Customer;
import com.devz.hotelmanagement.entities.InvoiceDetail;
import com.devz.hotelmanagement.entities.UsedService;
import com.devz.hotelmanagement.models.*;

import java.util.Date;

public interface HotelRoomService {

    HotelResp getHotel();

    void checkin(CheckinRoomReq checkinRoomReq);

    void cancel(String code, String note);

    void checkout(CheckoutRoomReq checkoutRoomReq);

    InvoiceDetail extendCheckoutDate(String code, Date extendDate, String note);

    void ready(String code);

    void change(String fromRoomCode, String toRoomCode, Date checkoutDate, String note);

    void payment(String invoiceCode, String promotionCode, String paymentMethodCode);

    void confirmPayment(String invoiceCode);

    void updateInvoiceDetail(InvoiceDetailUpdateReq invoiceDetailUpdateReq);

}

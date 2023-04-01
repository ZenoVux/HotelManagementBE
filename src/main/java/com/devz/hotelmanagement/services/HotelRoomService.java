package com.devz.hotelmanagement.services;

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

    UsedService usedService(UsedServiceReq usedServiceReq);

    void ready(String code);

    void change(String fromRoomCode, String toRoomCode, String note);

    void payment(String invoiceCode, String promotionCode, String paymentMethodCode);

}

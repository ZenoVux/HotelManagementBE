package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.InvoiceDetail;
import com.devz.hotelmanagement.models.CheckinRoomReq;
import com.devz.hotelmanagement.models.CheckoutRoom;
import com.devz.hotelmanagement.models.Hotel;

public interface HotelRoomService {

    Hotel getHotel();

    InvoiceDetail checkin(CheckinRoomReq checkinRoomReq);

    void cancel(String code, String note);

    InvoiceDetail checkout(CheckoutRoom checkoutRoom);

}

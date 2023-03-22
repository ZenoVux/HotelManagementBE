package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.Invoice;
import com.devz.hotelmanagement.entities.InvoiceDetail;
import com.devz.hotelmanagement.models.Hotel;

public interface HotelRoomService {

    Hotel getHotel();

    InvoiceDetail checkin(String code);

    void cancel(String code, String note);

    Invoice checkout(String[] codes);

}

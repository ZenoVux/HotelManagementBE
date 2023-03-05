package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.BookingDetail;
import com.devz.hotelmanagement.entities.InvoiceDetail;

public interface InvoiceDetailService extends ServiceBase<InvoiceDetail> {

    InvoiceDetail findByCheckoutRoomCode(String code);
}

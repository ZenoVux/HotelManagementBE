package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.Invoice;

public interface InvoiceService extends ServiceBase<Invoice> {

    Invoice findCurrByRoomCode(String code);

}

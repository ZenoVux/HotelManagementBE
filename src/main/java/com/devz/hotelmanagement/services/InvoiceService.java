package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.Invoice;
import com.devz.hotelmanagement.models.InvoiceStatusCountResp;

import java.util.List;

public interface InvoiceService extends ServiceBase<Invoice> {

    Invoice findCurrByRoomCode(String code);

    Invoice findFirstByBookingId(Integer id);

    List<InvoiceStatusCountResp> getStatusCount();

    List<Invoice> findByStatus(Integer status);

    Integer countInvoiceByPeopleId(String peopleId);

}

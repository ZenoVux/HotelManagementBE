package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.Invoice;
import com.devz.hotelmanagement.models.InvoiceResp;
import com.devz.hotelmanagement.models.InvoiceStatusCountResp;

import java.util.Date;
import java.util.List;

public interface InvoiceService extends ServiceBase<Invoice> {

    Invoice findCurrByRoomCode(String code);

    Invoice findFirstByBookingId(Integer id);

    List<InvoiceStatusCountResp> getStatusCount();

    List<Invoice> findByStatus(Integer status);

    List<InvoiceResp> findByAllResp();

    List<InvoiceResp> findByAllRespByStatus(Integer status);

    Integer countInvoiceByPeopleId(String peopleId);

    List<InvoiceResp> findByAllRespByStatusAndRangeDate(Integer status, Date startDate, Date endDate);

    List<InvoiceResp> findByAllRespByRangeDate(Date startDate, Date endDate);
}

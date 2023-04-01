package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.BookingDetail;

import java.util.Date;
import java.util.List;

public interface BookingDetailService extends ServiceBase<BookingDetail> {

    BookingDetail findWaitingCheckinByRoomCode(String code);

    BookingDetail findByCheckedinRoomCode(String code);

    BookingDetail findByInvoiceDetailId(Integer id);

    List<BookingDetail> createAll(List bookingDetails);

    List<BookingDetail>  findByBookingId(Integer id);

    List<BookingDetail> findByRoomCodeAndCheckinAndCheckout(String code, Date checkin, Date checkout);

}

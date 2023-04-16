package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.BookingDetail;

import java.util.Date;
import java.util.List;

public interface BookingDetailService extends ServiceBase<BookingDetail> {

    BookingDetail findWaitingCheckinByRoomCode(String code);

    BookingDetail findByCheckedinRoomCode(String code);

    void deleteById(Integer id);

    List<BookingDetail> findAllWaitingCheckin();

    List<BookingDetail> createAll(List<BookingDetail> bookingDetails);

    List<BookingDetail>  findByBookingId(Integer id);

    List<BookingDetail> findByRoomCodeAndCheckinAndCheckout(String code, Date checkin, Date checkout);

}

package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.BookingDetail;
import com.devz.hotelmanagement.models.BookingDetailEdit;

import java.util.Date;
import java.util.List;

public interface BookingDetailService extends ServiceBase<BookingDetail> {

    BookingDetail findWaitingCheckinByRoomCode(String code);

    BookingDetail findByCheckedinRoomCode(String code);

    void deleteById(Integer id);

    List<BookingDetail> findAllWaitingCheckin();

    List<BookingDetail> createAll(List<BookingDetail> bookingDetails);

    List<BookingDetail> updateAll(List<BookingDetail> bookingDetails);

    List<BookingDetail>  findByBookingId(Integer id);

    List<BookingDetail> findByRoomCodeAndCheckinAndCheckout(String code, Date checkin, Date checkout);

    List<Object[]> getInfoBookingDetail(Integer id);

    List<BookingDetail> checkRoomInRangeDay(String roomCode, Date checkin, Date checkout, String bookingCode);

}

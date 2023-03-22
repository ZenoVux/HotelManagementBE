package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.BookingDetail;

import java.util.List;

public interface BookingDetailService extends ServiceBase<BookingDetail> {

    BookingDetail findByCheckinRoomCode(String code);

    List<BookingDetail> createAll(List bookingDetails);

    List<BookingDetail>  findByBookingId(Integer id);

}

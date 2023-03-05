package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.BookingDetail;

public interface BookingDetailService extends ServiceBase<BookingDetail> {

    BookingDetail findByCheckinRoomCode(String code);
}

package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.Booking;
import com.devz.hotelmanagement.entities.BookingHistory;

import java.util.List;

public interface BookingHistoryService extends ServiceBase<BookingHistory> {
    void updateBeforeEditBooking(Booking booking);

    List<BookingHistory> findByBookingId (Integer id);

}

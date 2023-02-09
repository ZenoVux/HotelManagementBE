package com.devz.hotelmanagement.services;

import java.util.List;

import com.devz.hotelmanagement.entities.BookingDetail;


public interface BookingDetailService {
    List<BookingDetail> findAll();

    BookingDetail findById(int id);

    BookingDetail create(BookingDetail bookingDetail);

    BookingDetail update(BookingDetail bookingDetail);
}

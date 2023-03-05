package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.Booking;

import java.util.List;

public interface BookingService extends ServiceBase<Booking> {

    List<Object[]> getInfoRoomBooking();
}

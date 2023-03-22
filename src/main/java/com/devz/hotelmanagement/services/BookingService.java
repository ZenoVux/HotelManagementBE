package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.Booking;
import com.devz.hotelmanagement.models.BookingInfo;

import java.util.Date;
import java.util.List;

public interface BookingService extends ServiceBase<Booking> {

    List<Object[]> getInfoRoomBooking(String roomType, Date checkinDate, Date checkoutDate);

    List<Integer> getRoomsByTimeBooking(String categoryName, Date checkinDate, Date checkoutDate);

    List<Object[]> getBooking();

}

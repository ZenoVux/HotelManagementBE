package com.devz.hotelmanagement.services;

import java.util.List;

import com.devz.hotelmanagement.entities.Booking;

public interface BookingService {
	List<Booking> findAll();

	Booking findById(int id);

	Booking create(Booking booking);

	Booking upadte(Booking booking);
}

package com.devz.hotelmanagement.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devz.hotelmanagement.entities.Booking;
import com.devz.hotelmanagement.repositories.BookingRepository;
import com.devz.hotelmanagement.services.BookingService;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepo;

    @Override
    public List<Booking> findAll() {
        return bookingRepo.findAll();
    }

    @Override
    public Booking findById(int id) {
        Optional<Booking> optional = bookingRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public Booking create(Booking booking) {
        booking.setId(null);
        return bookingRepo.save(booking);
    }

    @Override
    public Booking update(Booking booking) {
        return bookingRepo.save(booking);
    }

}

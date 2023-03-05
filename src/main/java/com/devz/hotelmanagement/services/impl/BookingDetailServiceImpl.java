package com.devz.hotelmanagement.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devz.hotelmanagement.entities.BookingDetail;
import com.devz.hotelmanagement.repositories.BookingDetailRepository;
import com.devz.hotelmanagement.services.BookingDetailService;

@Service
public class BookingDetailServiceImpl implements BookingDetailService {

    @Autowired
    private BookingDetailRepository bookingDetailRepo;

    @Override
    public List<BookingDetail> findAll() {
        return bookingDetailRepo.findAll();
    }

    @Override
    public BookingDetail findById(int id) {
        Optional<BookingDetail> optional = bookingDetailRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public BookingDetail findByCode(String code) {
        return null;
    }

    @Override
    public BookingDetail create(BookingDetail bookingDetail) {
        bookingDetail.setId(null);
        return bookingDetailRepo.save(bookingDetail);
    }

    @Override
    public BookingDetail update(BookingDetail bookingDetail) {
        return bookingDetailRepo.save(bookingDetail);
    }

    @Override
    public BookingDetail findByCheckinRoomCode(String code) {
        Optional<BookingDetail> optional = bookingDetailRepo.findByCheckinRoomCode(code);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }
}

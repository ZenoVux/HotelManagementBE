package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.BookingDetailHistory;
import com.devz.hotelmanagement.repositories.BookingDetailHistoryRepository;
import com.devz.hotelmanagement.services.BookingDetailHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingDetailHistoryServiceImpl implements BookingDetailHistoryService {

    @Autowired
    private BookingDetailHistoryRepository bookingDetailHistoryRepo;

    @Override
    public List<BookingDetailHistory> findAll() {
        return bookingDetailHistoryRepo.findAll();
    }

    @Override
    public BookingDetailHistory findById(int id) {
        Optional<BookingDetailHistory> optional = bookingDetailHistoryRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public BookingDetailHistory findByCode(String code) {
        return null;
    }

    @Override
    public BookingDetailHistory create(BookingDetailHistory bookingDetailHistory) {
        bookingDetailHistory.setId(null);
        return bookingDetailHistoryRepo.save(bookingDetailHistory);
    }

    @Override
    public BookingDetailHistory update(BookingDetailHistory bookingDetailHistory) {
        return bookingDetailHistoryRepo.save(bookingDetailHistory);
    }
}

package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.Booking;
import com.devz.hotelmanagement.entities.BookingDetail;
import com.devz.hotelmanagement.entities.BookingDetailHistory;
import com.devz.hotelmanagement.entities.BookingHistory;
import com.devz.hotelmanagement.repositories.BookingDetailHistoryRepository;
import com.devz.hotelmanagement.repositories.BookingHistoryRepository;
import com.devz.hotelmanagement.services.BookingDetailHistoryService;
import com.devz.hotelmanagement.services.BookingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingHistoryServiceImpl implements BookingHistoryService {

    @Autowired
    private BookingHistoryRepository bookingHistoryRepo;

    @Autowired
    private BookingDetailHistoryRepository bookingDetailHistoryRepo;

    @Override
    public List<BookingHistory> findAll() {
        return bookingHistoryRepo.findAll();
    }

    @Override
    public BookingHistory findById(int id) {
        Optional<BookingHistory> optional = bookingHistoryRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public BookingHistory findByCode(String code) {
        return null;
    }

    @Override
    public BookingHistory create(BookingHistory bookingHistory) {
        bookingHistory.setId(null);
        return bookingHistoryRepo.save(bookingHistory);
    }

    @Override
    public BookingHistory update(BookingHistory bookingHistory) {
        return bookingHistoryRepo.save(bookingHistory);
    }

    @Override
    public void updateBeforeEditBooking(Booking booking) {
        List<BookingDetail> bookingDetails = booking.getBookingDetails();
        List<BookingDetailHistory> bookingDetailHistories = new ArrayList<>();

        BookingHistory bookingHistory = new BookingHistory(booking.getAccount(), booking, booking.getCreatedDate(), booking.getNumAdults(), booking.getNumChildren(), booking.getDeposit(), booking.getNote(), null);

        for (BookingDetail bookingDetail : bookingDetails) {
            BookingDetailHistory bookingDetailHistory = new BookingDetailHistory(bookingHistory, bookingDetail.getRoom(), bookingDetail.getNote(), bookingDetail.getCheckinExpected(), bookingDetail.getCheckoutExpected(), bookingDetail.getCreatedDate());
            bookingDetailHistories.add(bookingDetailHistory);
        }

        bookingHistoryRepo.save(bookingHistory);
        bookingDetailHistoryRepo.saveAll(bookingDetailHistories);

    }

    @Override
    public List<BookingHistory> findByBookingId(Integer id) {
        return bookingHistoryRepo.findByBookingId(id);
    }

}

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

        BookingHistory bookingHistory = new BookingHistory();
        List<BookingDetailHistory> bookingDetailHistories = new ArrayList<>();;

        for (BookingDetail bookingDetail : bookingDetails) {
            BookingDetailHistory bookingDetailHistory = new BookingDetailHistory();

            bookingDetailHistory.setBookingHistory(bookingHistory);
            bookingDetailHistory.setRoom(bookingDetail.getRoom());
            bookingDetailHistory.setCheckinExpected(bookingDetail.getCheckinExpected());
            bookingDetailHistory.setCheckoutExpected(bookingDetail.getCheckoutExpected());
            bookingDetailHistory.setNote(bookingDetail.getNote());

            bookingDetailHistories.add(bookingDetailHistory);
        }

        bookingHistory.setBooking(booking);
        bookingHistory.setCreatedDate(booking.getCreatedDate());
        bookingHistory.setNumAdults(booking.getNumAdults());
        bookingHistory.setNumChildren(booking.getNumChildren());
        bookingHistory.setDeposit(booking.getDeposit());
        bookingHistory.setNote(booking.getNote());
        bookingHistory.setBookingDetailHistories(bookingDetailHistories);

        bookingHistoryRepo.save(bookingHistory);
        bookingDetailHistoryRepo.saveAll(bookingDetailHistories);
    }
}

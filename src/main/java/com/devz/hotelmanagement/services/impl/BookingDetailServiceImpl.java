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
        try {
            String maxCode = bookingDetailRepo.getMaxCode();
            Integer index = 1;
            if (maxCode != null) {
                index = Integer.parseInt(maxCode.replace("BKD", ""));
            }
            String code = String.format("BKD%05d", index + 1);
            bookingDetail.setCode(code);
        } catch (Exception ex) {

        }
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

    @Override
    public BookingDetail findByCheckedinRoomCode(String code) {
        Optional<BookingDetail> optional = bookingDetailRepo.findByCheckedinRoomCode(code);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public BookingDetail findByInvoiceDetailId(Integer id) {
        Optional<BookingDetail> optional = bookingDetailRepo.findByInvoiceDetailId(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

}

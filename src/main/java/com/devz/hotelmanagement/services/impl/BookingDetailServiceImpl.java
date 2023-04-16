package com.devz.hotelmanagement.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.devz.hotelmanagement.models.BookingDetailEdit;
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
        return bookingDetailRepo.findByCode(code);
    }

    @Override
    public BookingDetail create(BookingDetail bookingDetail) {
        bookingDetail.setId(null);
        try {
            String maxCode = bookingDetailRepo.getMaxCode();
            Integer index = 1;
            if (maxCode != null) {
                index = Integer.parseInt(maxCode.replace("BKD", ""));
                index++;
            }
            String code = String.format("BKD%05d", index);
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
    public BookingDetail findWaitingCheckinByRoomCode(String code) {
        Optional<BookingDetail> optional = bookingDetailRepo.findWaitingCheckinByRoomCode(code);
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
    public void deleteById(Integer id) {
        bookingDetailRepo.deleteById(id);
    }

    @Override
    public List<BookingDetail> findAllWaitingCheckin() {
        return bookingDetailRepo.findAllWaitingCheckin();
    }

    @Override
    public List<BookingDetail> createAll(List<BookingDetail> bookingDetails) {
        try {
            String maxCode = bookingDetailRepo.getMaxCode();
            Integer index = 1;
            if (maxCode != null) {
                index = Integer.parseInt(maxCode.replace("BKD", ""));
                index++;
            }
            for (BookingDetail detail : bookingDetails) {
                String code = String.format("BKD%05d", index++);
                detail.setCode(code);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return bookingDetailRepo.saveAll(bookingDetails);
    }

    @Override
    public List<BookingDetail> findByBookingId(Integer id) {
        return bookingDetailRepo.findByBookingId(id);
    }

    @Override
    public List<BookingDetail> findByRoomCodeAndCheckinAndCheckout(String code, Date checkin, Date checkout) {
        return bookingDetailRepo.findByRoomCodeAndCheckinAndCheckout(code, checkin, checkout);
    }

    @Override
    public List<Object[]> getInfoBookingDetail(Integer id) {
        return bookingDetailRepo.getInfoBookingDetail(id);
    }

    @Override
    public List<BookingDetail> checkRoomInRangeDay(String roomCode, Date checkin, Date checkout, String bookingCode) {
        return bookingDetailRepo.checkRoomInRangeDay(roomCode, checkin, checkout, bookingCode);
    }

}

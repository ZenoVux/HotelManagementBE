package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.Booking;
import com.devz.hotelmanagement.entities.BookingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingHistoryRepository extends JpaRepository<BookingHistory, Integer> {

}

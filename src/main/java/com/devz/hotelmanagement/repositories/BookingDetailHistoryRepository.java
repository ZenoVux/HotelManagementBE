package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.BookingDetailHistory;
import com.devz.hotelmanagement.entities.BookingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingDetailHistoryRepository extends JpaRepository<BookingDetailHistory, Integer> {

    @Query(value = "SELECT bdh FROM BookingDetailHistory bdh WHERE bdh.bookingHistory.id = :id")
    List<BookingDetailHistory> findByBookingHistoryId(@Param("id") Integer id);

}

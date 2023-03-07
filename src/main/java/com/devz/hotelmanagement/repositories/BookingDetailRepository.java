package com.devz.hotelmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devz.hotelmanagement.entities.BookingDetail;

import java.util.Optional;

@Repository
public interface BookingDetailRepository extends JpaRepository<BookingDetail, Integer> {

    @Query("SELECT bd FROM BookingDetail bd WHERE bd.room.code = :code " +
            "AND bd.booking.checkinExpected <= CURRENT_DATE " +
            "AND bd.booking.checkoutExpected >= CURRENT_DATE " +
            "AND bd.room.status = 0")
    Optional<BookingDetail> findByCheckinRoomCode(@Param("code") String code);

}

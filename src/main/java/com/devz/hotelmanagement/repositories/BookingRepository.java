package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devz.hotelmanagement.entities.Booking;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query(value = "CALL GET_INFO_ROOMS_BY_TIMES_BOOKING(:roomType, :checkinDate, :checkoutDate)", nativeQuery = true)
    List<Object[]> getInfoRoomBooking(
            @Param("roomType") String roomType,
            @Param("checkinDate") Date checkinDate,
            @Param("checkoutDate") Date checkoutDate
    );

    @Query(value = "CALL GET_ROOMS_BY_TIMES_BOOKING(:categoryName, :checkinDate, :checkoutDate)", nativeQuery = true)
    List<Integer> getRoomsByTimeBooking(
            @Param("categoryName") String categoryName,
            @Param("checkinDate") Date checkinDate,
            @Param("checkoutDate") Date checkoutDate
    );

    @Query("SELECT b FROM Booking b JO")
    Optional<Booking> findByInvoiceCode(String code);
}

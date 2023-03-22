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
            "AND bd.checkinExpected <= CURRENT_DATE " +
            "AND bd.checkoutExpected >= CURRENT_DATE " +
            "AND bd.room.status = 0 AND bd.status = 0")
    Optional<BookingDetail> findByCheckinRoomCode(@Param("code") String code);

    @Query("SELECT bd FROM BookingDetail bd WHERE bd.room.code = :code " +
            "AND bd.room.status = 2 AND bd.status = 1")
    Optional<BookingDetail> findByCheckedinRoomCode(@Param("code") String code);

    @Query("SELECT bd FROM BookingDetail bd WHERE bd.invoiceDetail.id = :id")
    Optional<BookingDetail> findByInvoiceDetailId(@Param("id") Integer id);

    @Query(value = "SELECT booking_details.code FROM booking_details ORDER BY booking_details.code DESC LIMIT 1", nativeQuery = true)
    String getMaxCode();

}

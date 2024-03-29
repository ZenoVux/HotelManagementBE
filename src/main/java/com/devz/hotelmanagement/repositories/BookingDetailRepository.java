package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.models.BookingDetailEdit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devz.hotelmanagement.entities.BookingDetail;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingDetailRepository extends JpaRepository<BookingDetail, Integer> {

    // Tìm BKD trong
    @Query("SELECT bd FROM BookingDetail bd WHERE bd.room.code = :code " +
            "AND bd.checkinExpected <= CURRENT_DATE " +
            "AND bd.checkoutExpected > CURRENT_DATE " +
            "AND (bd.booking.status = 2 OR bd.booking.status = 3) AND bd.status = 1 AND bd.room.status = 0")
    Optional<BookingDetail> findWaitingCheckinByRoomCode(@Param("code") String code);

    @Query("SELECT bd FROM BookingDetail bd WHERE bd.room.code = :code " +
            "AND bd.room.status = 2 AND bd.status = 1")
    Optional<BookingDetail> findByCheckedinRoomCode(@Param("code") String code);

    @Query("SELECT bd FROM BookingDetail bd WHERE bd.room.code = :id")
    Optional<BookingDetail> findByRoomCodeAndBookingId(@Param("id") Integer id);

    @Query(value = "SELECT booking_details.code FROM booking_details ORDER BY booking_details.code DESC LIMIT 1", nativeQuery = true)
    String getMaxCode();

    @Query("SELECT bd FROM BookingDetail bd WHERE bd.booking.id = :id")
    List<BookingDetail> findByBookingId(@Param("id") Integer id);

    @Query(value = "SELECT booking_details.* " +
            "FROM bookings " +
            "   JOIN booking_details ON bookings.id = booking_details.booking_id " +
            "   JOIN rooms ON booking_details.room_id = rooms.id " +
            "WHERE " +
            "   (DATE(booking_details.checkin_expected) < DATE(:checkout)) AND " +
            "   (DATE(booking_details.checkout_expected) > DATE(:checkin)) AND " +
            "   booking_details.status = 1 AND (bookings.status = 2 OR bookings.status = 3) AND rooms.code = :code", nativeQuery = true)
    List<BookingDetail> findByRoomCodeAndCheckinAndCheckout(@Param("code") String code, @Param("checkin") Date checkin, @Param("checkout") Date checkout);

    @Query("SELECT bd FROM BookingDetail bd " +
            "WHERE bd.checkinExpected <= CURRENT_DATE " +
            "AND bd.checkoutExpected > CURRENT_DATE " +
            "AND (bd.booking.status = 2 OR bd.booking.status = 3) AND bd.status = 1 AND bd.room.status = 0")
    List<BookingDetail> findAllWaitingCheckin();

    @Query(value = "CALL GET_INFO_BOOKINGDETAIL(:id)", nativeQuery = true)
    List<Object[]> getInfoBookingDetail(@Param("id") Integer id);

    @Query(value = "SELECT bkd " +
            "FROM BookingDetail bkd " +
            "WHERE bkd.room.code = :roomCode " +
            "   AND bkd.checkinExpected >= :checkin " +
            "   AND bkd.checkoutExpected <= :checkout " +
            "   AND bkd.booking.code != :bookingCode")
    List<BookingDetail> checkRoomInRangeDay(@Param("roomCode") String roomCode, @Param("checkin") Date checkin, @Param("checkout") Date checkout, @Param("bookingCode") String bookingCode);

    @Query("SELECT bd FROM BookingDetail bd WHERE bd.code = :code")
    BookingDetail findByCode(@Param("code") String code);

}

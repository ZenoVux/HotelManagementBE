package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.Room;
import com.devz.hotelmanagement.models.BookingInfo;
import com.devz.hotelmanagement.models.NumberRoomBookingOnl;
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

//    @Query(value = "CALL GET_INFO_ROOMS_BY_TIMES_BOOKING(:roomType, :checkinDate, :checkoutDate)", nativeQuery = true)
    @Query(value = "CALL MINH_TEST(:roomType, :checkinDate, :checkoutDate)", nativeQuery = true)
    List<Object[]> getInfoRoomBooking(
            @Param("roomType") String roomType,
            @Param("checkinDate") Date checkinDate,
            @Param("checkoutDate") Date checkoutDate
    );

//    @Query(value = "CALL GET_ROOMS_BY_TIMES_BOOKING(:categoryName, :checkinDate, :checkoutDate)", nativeQuery = true)
    @Query(value = "CALL MINH_TEST_2(:categoryName, :checkinDate, :checkoutDate)", nativeQuery = true)
    List<Integer> getRoomsByTimeBooking(
            @Param("categoryName") String categoryName,
            @Param("checkinDate") Date checkinDate,
            @Param("checkoutDate") Date checkoutDate
    );

    @Query("SELECT b FROM Booking b WHERE b.code = :code")
    Optional<Booking> findByInvoiceCode(@Param("code") String code);

    @Query(value= "CALL GET_INFO_BOOKING(:startDate, :endDate)", nativeQuery = true)
    List<Object[]> getBooking( @Param("startDate") Date startDate,
                               @Param("endDate") Date endDate);

    @Query(value = "SELECT b FROM Booking b WHERE b.code = :code")
    Booking findByCode(@Param("code") String code);

    @Query(value = "SELECT bookings.code FROM bookings ORDER BY bookings.code DESC LIMIT 1", nativeQuery = true)
    String getMaxCode();

    @Query(value = "SELECT b FROM Booking b WHERE b.customer.id = :id")
    List<Booking> getBookingByCusId(@Param("id") Integer id);

    @Query(value =
            "SELECT " +
            "   CONCAT(rt.name, ' | ', GROUP_CONCAT(DISTINCT CONCAT(br.quantity_bed, ' ', bt.name) ORDER BY bt.name SEPARATOR ' & ')) AS category_name, " +
            "   COUNT(DISTINCT r.id) as quantity " +
            "FROM rooms r " +
            "   LEFT JOIN room_types rt ON r.room_type_id = rt.id " +
            "   LEFT JOIN bed_rooms br ON r.id = br.room_id " +
            "   LEFT JOIN bed_types bt ON br.bed_type_id = bt.id " +
            "   LEFT JOIN booking_details bkd ON r.id = bkd.room_id " +
            "   LEFT JOIN bookings bk ON bkd.booking_id = bk.id " +
            "WHERE bk.status = 1 " +
                    "AND r.code IN " +
                    "( " +
                    "SELECT rooms.code " +
                    "FROM bookings " +
                    "   JOIN booking_details ON bookings.id = booking_details.booking_id " +
                    "   JOIN rooms ON booking_details.room_id = rooms.id " +
                    "WHERE " +
                    "((DATE(booking_details.checkin_expected) >= :checkin AND DATE(booking_details.checkin_expected) < :checkout) " +
                    "   OR (DATE(booking_details.checkout_expected) > :checkin AND DATE(booking_details.checkout_expected) <= :checkout) " +
                    "   OR (DATE(booking_details.checkin_expected) <= :checkin AND DATE(booking_details.checkout_expected) >= :checkout)) " +
                    "AND bookings.status = 1 " +
                    ") " +
            "GROUP BY rt.name, r.room_type_id, r.num_adults, r.num_childs " +
            "ORDER BY r.room_type_id ", nativeQuery = true)
    List<Object[]> getNumberRoomBookingOnl(@Param("checkin") Date checkin, @Param("checkout") Date checkout);

}

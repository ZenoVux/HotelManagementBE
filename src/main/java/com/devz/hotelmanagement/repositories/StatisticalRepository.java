package com.devz.hotelmanagement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devz.hotelmanagement.entities.BookingDetail;

@Repository
public interface StatisticalRepository extends JpaRepository<BookingDetail, Integer> {

    @Query(value = "SELECT r.code, COUNT(*) AS numBookings " + "FROM rooms r "
            + "INNER JOIN booking_details bd ON bd.room_id = r.id "
            + "INNER JOIN bookings b ON bd.booking_id = b.id AND b.status = 2 " + "GROUP BY r.id "
            + "ORDER BY numBookings DESC " + "LIMIT 4;", nativeQuery = true)
    List<Object[]> findRoomsByNumBookings(); // top 4 room

    @Query(value = "SELECT rt.name, COUNT(*) AS numBookings " + "FROM room_types rt "
            + "INNER JOIN rooms r ON r.room_type_id = rt.id " + "INNER JOIN booking_details bd ON bd.room_id = r.id "
            + "INNER JOIN bookings b ON bd.booking_id = b.id AND b.status = 2 " + "GROUP BY rt.id "
            + "ORDER BY numBookings DESC " + "LIMIT 4", nativeQuery = true)
    List<Object[]> findRoomTypesByNumBookings(); // top 4 type

    @Query(value = "SELECT sr.name, SUM(us.quantity) as totalQuantity\n" + "FROM used_services us\n"
            + "         JOIN invoice_details id ON us.invoice_detail_id = id.id\n"
            + "         JOIN services sr ON us.service_id = sr.id\n" + "WHERE id.status = 2 AND us.status = true " + "GROUP BY sr.name\n"
            + "ORDER BY totalQuantity DESC\n" + "LIMIT 4;", nativeQuery = true)
    List<Object[]> findTop4ServicesUsed();

    @Query(value = "SELECT 'Today' AS period, COUNT(*) AS totalBooked\n" + "FROM bookings\n"
            + "WHERE status = 2 AND DATE(created_date) = CURDATE()\n" + "UNION\n"
            + "SELECT 'This Week' AS period, COUNT(*) AS totalBooked\n" + "FROM bookings\n"
            + "WHERE status = 2 AND WEEK(created_date, 1) = WEEK(CURDATE(), 1)\n" + "UNION\n"
            + "SELECT 'This Month' AS period, COUNT(*) AS totalBooked\n" + "FROM bookings\n"
            + "WHERE MONTH(created_date) = MONTH(CURRENT_DATE()) AND YEAR(created_date) = YEAR(CURRENT_DATE()) AND status = 2;", nativeQuery = true)
    List<Object[]> getTotal();

    @Query(value = "SELECT COUNT(*) FROM bookings\n"
            + "WHERE created_date BETWEEN :start AND DATE_ADD(:end, INTERVAL 1 DAY)\n"
            + "  AND status = 2;", nativeQuery = true)
    Integer getTotalByDateRange(@Param("start") String start, @Param("end") String end);

    @Query(value = "SELECT rt.name,\n" +
            "       COALESCE(SUM(CASE WHEN DAYNAME(b.created_date) = 'Monday' THEN 1 ELSE 0 END), 0) AS 'Monday',\n" +
            "       COALESCE(SUM(CASE WHEN DAYNAME(b.created_date) = 'Tuesday' THEN 1 ELSE 0 END), 0) AS 'Tuesday',\n" +
            "       COALESCE(SUM(CASE WHEN DAYNAME(b.created_date) = 'Wednesday' THEN 1 ELSE 0 END), 0) AS 'Wednesday',\n" +
            "       COALESCE(SUM(CASE WHEN DAYNAME(b.created_date) = 'Thursday' THEN 1 ELSE 0 END), 0) AS 'Thursday',\n" +
            "       COALESCE(SUM(CASE WHEN DAYNAME(b.created_date) = 'Friday' THEN 1 ELSE 0 END), 0) AS 'Friday',\n" +
            "       COALESCE(SUM(CASE WHEN DAYNAME(b.created_date) = 'Saturday' THEN 1 ELSE 0 END), 0) AS 'Saturday',\n" +
            "       COALESCE(SUM(CASE WHEN DAYNAME(b.created_date) = 'Sunday' THEN 1 ELSE 0 END), 0) AS 'Sunday'\n" +
            "FROM room_types rt\n" +
            "         LEFT JOIN rooms r ON r.room_type_id = rt.id\n" +
            "         LEFT JOIN booking_details bd ON bd.room_id = r.id\n" +
            "         LEFT JOIN bookings b ON bd.booking_id = b.id AND b.status = 2\n" +
            "WHERE b.created_date BETWEEN DATE(DATE_SUB(NOW(), INTERVAL WEEKDAY(NOW()) DAY)) AND DATE(DATE_ADD(NOW(), INTERVAL 6 - WEEKDAY(NOW()) DAY))\n" +
            "GROUP BY rt.id;", nativeQuery = true)
    List<Object[]> getTypeRoomGoogleChart(); // so do tang truong google chart

    @Query(value = "SELECT t1.period, t1.total_booked, COALESCE(t2.total_revenue, 0) AS total_revenue\n" +
            "FROM (\n" +
            "         SELECT rt.name AS period, COUNT(*) AS total_booked\n" +
            "         FROM room_types rt\n" +
            "                  INNER JOIN rooms r ON r.room_type_id = rt.id\n" +
            "                  INNER JOIN booking_details bd ON bd.room_id = r.id\n" +
            "                  INNER JOIN bookings b ON bd.booking_id = b.id AND b.status = 2\n" +
            "         GROUP BY rt.id\n" +
            "     ) t1\n" +
            "         LEFT JOIN (\n" +
            "    SELECT room_types.name, SUM(invoice_details.total) AS total_revenue\n" +
            "    FROM invoices\n" +
            "             INNER JOIN invoice_details ON invoices.id = invoice_details.invoice_id\n" +
            "             INNER JOIN rooms ON invoice_details.room_id = rooms.id\n" +
            "             INNER JOIN room_types ON rooms.room_type_id = room_types.id\n" +
            "    WHERE invoices.status = 4\n" +
            "    GROUP BY room_types.id\n" +
            ") t2 ON t1.period = t2.name;", nativeQuery = true)
    List<Object[]> getReportExcel(); // xuat Excel


}

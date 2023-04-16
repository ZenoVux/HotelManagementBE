package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.Invoice;

import com.devz.hotelmanagement.models.InvoiceResp;
import com.devz.hotelmanagement.models.InvoiceStatusCountResp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    @Query("SELECT iv FROM Invoice iv " +
            "JOIN InvoiceDetail ivd ON iv.id = ivd.invoice.id " +
            "JOIN Room r ON r.id = ivd.room.id " +
            "WHERE iv.status = 0 AND r.status = 2 AND r.code = :code")
    Optional<Invoice> findCurrByRoomCode(@Param("code") String code);

    @Query("SELECT iv FROM Invoice iv WHERE iv.code = :code")
    Optional<Invoice> findByRoomCode(@Param("code") String code);

    @Query(value = "SELECT * FROM invoices WHERE invoices.booking_id = :id AND invoices.status = 1 ORDER BY invoices.created_date ASC LIMIT 1", nativeQuery = true)
    Optional<Invoice>  findFirstByBookingId(@Param("id") Integer id);

    @Query("SELECT new com.devz.hotelmanagement.models.InvoiceStatusCountResp(iv.status, COUNT(iv)) FROM Invoice iv GROUP BY iv.status")
    List<InvoiceStatusCountResp> getStatusCount();

    @Query("SELECT iv FROM Invoice iv WHERE iv.status = :status")
    List<Invoice> findByStatus(Integer status);

    @Query(value = "SELECT invoices.code FROM invoices ORDER BY invoices.code DESC LIMIT 1", nativeQuery = true)
    String getMaxCode();

    @Query(value = "SELECT 'Today' AS period, SUM(total_payment) AS total_payment " +
            "FROM invoices " +
            "WHERE status = 4 AND DATE(paid_date) = CURDATE() " +
            "UNION " +
            "SELECT 'This month' AS period, SUM(total_payment) AS total_payment " +
            "FROM invoices " +
            "WHERE status = 4 AND MONTH(paid_date) = MONTH(CURDATE()) AND YEAR(paid_date) = YEAR(CURDATE()) " +
            "UNION " +
            "SELECT 'This week' AS period, SUM(total_payment) AS total_payment " +
            "FROM invoices " +
            "WHERE status = 4 AND WEEK(paid_date, 1) = WEEK(CURDATE(), 1);", nativeQuery = true)
    List<Object[]> getTotalByToday();

    @Query(value = "SELECT 'Yesterday' AS period, SUM(total_payment) AS total_payment " +
            "FROM invoices " +
            "WHERE status = 4 AND DATE(paid_date) = DATE_SUB(CURDATE(), INTERVAL 1 DAY) " +
            "UNION " +
            "SELECT 'Last week' AS period, SUM(total_payment) AS total_payment " +
            "FROM invoices " +
            "WHERE status = 4 AND YEARWEEK(paid_date, 1) = YEARWEEK(DATE_SUB(CURDATE(), INTERVAL 1 WEEK), 1) " +
            "UNION " +
            "SELECT 'Last month' AS period, SUM(total_payment) AS total_payment " +
            "FROM invoices " +
            "WHERE status = 4 AND MONTH(paid_date) = MONTH(DATE_SUB(CURDATE(), INTERVAL 1 MONTH)) AND YEAR(paid_date) = YEAR(DATE_SUB(CURDATE(), INTERVAL 1 MONTH));", nativeQuery = true)
    List<Object[]> getTotalByYesterday();

    @Query(value = "SELECT SUM(total_payment) AS total_amount FROM invoices WHERE status = 4 AND paid_date BETWEEN :start AND DATE_ADD(:end, INTERVAL 1 DAY)", nativeQuery = true)
    Double getTotalAmountByDateRange(@Param("start") String start, @Param("end") String end);

    @Query("SELECT " +
            "new com.devz.hotelmanagement.models.InvoiceResp(" +
            "   iv.code, " +
            "   iv.booking.code, " +
            "   iv.booking.customer.fullName, " +
            "   iv.account.fullName, " +
            "   iv.total, " +
            "   iv.status," +
            "   iv.createdDate" +
            ") " +
            "FROM Invoice iv")
    List<InvoiceResp> findByAllResp();
}

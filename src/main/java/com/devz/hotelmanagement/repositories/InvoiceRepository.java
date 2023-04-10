package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.Invoice;

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
}

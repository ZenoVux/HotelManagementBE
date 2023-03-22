package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.Invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

    @Query(value = "SELECT invoices.code FROM invoices ORDER BY invoices.code DESC LIMIT 1", nativeQuery = true)
    String getMaxCode();
}

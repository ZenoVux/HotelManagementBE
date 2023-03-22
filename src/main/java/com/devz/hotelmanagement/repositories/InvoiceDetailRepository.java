package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Integer> {

    @Query("SELECT iv FROM InvoiceDetail iv WHERE iv.room.code = :code " +
            "AND iv.room.status = 2 AND iv.status = 0")
    Optional<InvoiceDetail> findByCheckoutRoomCode(@Param("code") String code);

    @Query("SELECT iv FROM InvoiceDetail iv WHERE iv.invoice.code = :code")
    List<InvoiceDetail> findByInvoiceCode(@Param("code") String code);

    @Query(value = "SELECT invoice_details.code FROM invoice_details ORDER BY invoice_details.code DESC LIMIT 1", nativeQuery = true)
    String getMaxCode();
}

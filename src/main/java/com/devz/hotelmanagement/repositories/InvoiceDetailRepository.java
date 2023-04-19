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

    @Query("SELECT ivd FROM InvoiceDetail ivd WHERE ivd.room.code = :code " +
            "AND ivd.room.status = 2 AND ivd.status = 1")
    Optional<InvoiceDetail> findUsingByRoomCode(@Param("code") String code);

    @Query("SELECT ivd FROM InvoiceDetail ivd WHERE ivd.invoice.code = :code")
    List<InvoiceDetail> findByInvoiceCode(@Param("code") String code);

    @Query(value = "SELECT invoice_details.code FROM invoice_details ORDER BY invoice_details.code DESC LIMIT 1", nativeQuery = true)
    String getMaxCode();

    @Query("SELECT ivd FROM InvoiceDetail ivd WHERE ivd.code = :code")
    Optional<InvoiceDetail> findByCode(@Param("code") String code);

    @Query("SELECT ivd FROM InvoiceDetail ivd WHERE ivd.room.status = 2 AND ivd.status = 1")
    List<InvoiceDetail> findAllUsing();

    @Query("SELECT ivd FROM InvoiceDetail ivd WHERE ivd.invoice.code = :code AND ivd.status = :status")
    List<InvoiceDetail> findByInvoiceCodeAndStatus(@Param("code") String code, @Param("status") Integer status);
}

package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Integer> {

    @Query("SELECT iv FROM InvoiceDetail iv WHERE iv.room.code = :code AND iv.room.status = 2")
    Optional<InvoiceDetail> findByCheckoutRoomCode(@Param("code") String code);
}

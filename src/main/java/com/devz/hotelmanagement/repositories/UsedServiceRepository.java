package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.HostedAt;
import com.devz.hotelmanagement.entities.UsedService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsedServiceRepository extends JpaRepository<UsedService, Integer> {

    @Query("SELECT u FROM UsedService u WHERE u.bookingDetail.code = :code")
    List<UsedService> findAllByBookingDetailCode(@Param("code") String code);

    @Query("SELECT u FROM UsedService u WHERE u.invoiceDetail.id = :id")
    List<UsedService> findAllByInvoiceDetailId(Integer id);
}

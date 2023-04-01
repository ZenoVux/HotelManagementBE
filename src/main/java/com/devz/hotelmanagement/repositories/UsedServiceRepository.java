package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.HostedAt;
import com.devz.hotelmanagement.entities.UsedService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsedServiceRepository extends JpaRepository<UsedService, Integer> {

    @Query("SELECT u FROM UsedService u WHERE u.bookingDetail.code = :code")
    List<UsedService> findAllByBookingDetailCode(@Param("code") String code);

    @Query("SELECT u FROM UsedService u WHERE u.invoiceDetail.id = :id")
    List<UsedService> findAllByInvoiceDetailId(Integer id);

    @Query("SELECT u FROM UsedService u WHERE u.bookingDetail.id = :id")
    List<UsedService> findAllByBookingDetailId(Integer id);

    @Query("SELECT u FROM UsedService u WHERE u.serviceRoom.id = :serviceRoomId AND u.invoiceDetail.id = :invoiceDetailId")
    Optional<UsedService> findByServiceRoomIdAndInvoiceDetailId(@Param("serviceRoomId") Integer serviceRoomId, @Param("invoiceDetailId") Integer invoiceDetailId);
}

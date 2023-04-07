package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.UsedService;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsedServiceRepository extends JpaRepository<UsedService, Integer> {

    @Query("SELECT u FROM UsedService u WHERE u.invoiceDetail.id = :invoiceDetailId")
    List<UsedService> findAllByInvoiceDetailId(@Param("invoiceDetailId") Integer invoiceDetailId);

    @Query("SELECT u FROM UsedService u WHERE u.invoiceDetail.id = :invoiceDetailId AND u.status = :status")
    List<UsedService> findAllByInvoiceDetailIdAndStatus(@Param("invoiceDetailId") Integer invoiceDetailId, @Param("status") Boolean status);

    @Query("SELECT u " +
            "FROM UsedService u " +
            "WHERE u.serviceRoom.id = :serviceRoomId " +
            "AND u.invoiceDetail.id = :invoiceDetailId " +
            "AND u.status = :status")
    Optional<UsedService> findByServiceRoomIdAndInvoiceDetailIdAndStatus(
            @Param("serviceRoomId") Integer serviceRoomId,
            @Param("invoiceDetailId") Integer invoiceDetailId,
            @Param("status") Boolean status
    );

    @Query("SELECT u FROM UsedService u WHERE u.code = :code")
    Optional<UsedService> findByCode(@Param("code") String code);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
            "FROM UsedService u " +
            "WHERE u.invoiceDetail.id = :invoiceDetailId " +
            "AND u.serviceRoom.id = :serviceRoomId " +
            "AND u.startedTime < :endedTime " +
            "AND u.endedTime > :startedTime " +
            "AND u.status = :status")
    Boolean existsByServiceRoomIdAndInRangeStartedTimeToEndedTime(
            @Param("invoiceDetailId") Integer invoiceDetailId,
            @Param("serviceRoomId") Integer serviceRoomId,
            @Param("startedTime") Date startedTime,
            @Param("endedTime") Date endedTime,
            @Param("status") Boolean status
    );
}

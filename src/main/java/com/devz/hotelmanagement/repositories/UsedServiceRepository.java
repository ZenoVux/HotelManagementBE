package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.UsedService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsedServiceRepository extends JpaRepository<UsedService, Integer> {

    @Query("SELECT u FROM UsedService u WHERE u.invoiceDetail.id = :invoiceDetailId")
    List<UsedService> findAllByInvoiceDetailId(@Param("invoiceDetailId") Integer invoiceDetailId);

    @Query("SELECT u FROM UsedService u WHERE u.invoiceDetail.id = :invoiceDetailId AND u.isUsed = :isUsed")
    List<UsedService> findAllByInvoiceDetailIdAndIsUsed(@Param("invoiceDetailId") Integer invoiceDetailId, @Param("isUsed") Boolean isUsed);

    @Query("SELECT u " +
            "FROM UsedService u " +
            "WHERE u.serviceRoom.id = :serviceRoomId " +
            "AND u.invoiceDetail.id = :invoiceDetailId " +
            "AND u.isUsed = :isUsed")
    Optional<UsedService> findByServiceRoomIdAndInvoiceDetailIdAndIsUsed(
            @Param("serviceRoomId") Integer serviceRoomId,
            @Param("invoiceDetailId") Integer invoiceDetailId,
            @Param("isUsed") Boolean isUsed
    );

    @Query("SELECT u FROM UsedService u WHERE u.code = :code")
    Optional<UsedService> findByCode(@Param("code") String code);

    @Query(value = "SELECT used_services.code FROM used_services ORDER BY used_services.code DESC LIMIT 1", nativeQuery = true)
    String getMaxCode();

}

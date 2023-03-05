package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.HostedAt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HostedAtRepository extends JpaRepository<HostedAt, Integer> {

    @Query("SELECT h FROM HostedAt h WHERE h.bookingDetail.id = :id")
    List<HostedAt> findAllByBookingDetailId(@Param("id") Integer id);
}

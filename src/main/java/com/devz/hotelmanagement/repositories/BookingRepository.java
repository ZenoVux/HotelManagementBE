package com.devz.hotelmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devz.hotelmanagement.entities.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

}

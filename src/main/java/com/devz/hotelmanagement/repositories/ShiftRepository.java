package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.Shift;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftRepository extends JpaRepository<Integer, Shift> {

}

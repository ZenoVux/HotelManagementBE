package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.SupplyType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyTypeRepository extends JpaRepository<SupplyType, Integer> {

}

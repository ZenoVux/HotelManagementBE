package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.Supply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyRepository extends JpaRepository<Integer, Supply> {

}

package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.SupplyRoom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyRoomRepository extends JpaRepository<SupplyRoom, Integer> {

}

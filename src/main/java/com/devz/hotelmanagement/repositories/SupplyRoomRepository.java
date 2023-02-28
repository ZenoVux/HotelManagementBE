package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.SupplyRoom;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyRoomRepository extends JpaRepository<SupplyRoom, Integer> {

	@Query("SELECT p FROM SupplyRoom p WHERE p.room.code=?1")
	List<SupplyRoom> findByCodeRoom(String code);

}

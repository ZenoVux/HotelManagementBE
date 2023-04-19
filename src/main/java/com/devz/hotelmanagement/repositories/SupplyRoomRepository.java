package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.SupplyRoomType;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyRoomRepository extends JpaRepository<SupplyRoomType, Integer> {
	@Query("SELECT p FROM SupplyRoomType p WHERE p.room.code=?1")
	List<SupplyRoomType> findByCodeRoom(String code);
}

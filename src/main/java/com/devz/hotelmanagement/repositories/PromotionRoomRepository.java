package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.PromotionRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PromotionRoomRepository  extends JpaRepository<PromotionRoom,Integer> {
	@Query("SELECT ur FROM PromotionRoom ur WHERE ur.room.id = :roomtId")
    List<PromotionRoom> findByRoomId(@Param("roomtId")Integer id);
}

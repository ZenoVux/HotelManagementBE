package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.RoomTypePromotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomTypePromotionRepository extends JpaRepository<RoomTypePromotion,Integer> {

	@Query("SELECT ur FROM RoomTypePromotion ur WHERE ur.roomType.id = :roomTypeId")
    List<RoomTypePromotion> findByRoomTypeId(@Param("roomTypeId")Integer roomTypeId);

    @Query("SELECT p FROM RoomTypePromotion p " +
            "WHERE p.promotion.startedDate <= CURRENT_DATE " +
            "AND p.promotion.endedDate > CURRENT_DATE " +
            "AND p.promotion.type = false AND  p.promotion.status = true")
    List<RoomTypePromotion> findAllCurrForRoomType();

    @Query("SELECT p FROM RoomTypePromotion p " +
            "WHERE p.promotion.startedDate <= CURRENT_DATE " +
            "AND p.promotion.endedDate > CURRENT_DATE " +
            "AND p.promotion.type = false AND  p.promotion.status = true AND p.roomType.code = :code")
    Optional<RoomTypePromotion> findCurrByRoomTypeCode(@Param("code") String code);
}

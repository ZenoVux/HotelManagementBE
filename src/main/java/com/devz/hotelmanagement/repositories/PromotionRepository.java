package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.Promotion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {

    @Query("SELECT p FROM Promotion p  WHERE p.code = :code")
    Optional<Promotion> findByCode(@Param("code") String code);

    @Query("SELECT p " +
            "FROM Promotion p " +
            "WHERE p.startedDate <= CURRENT_DATE AND " +
            "   p.endedDate >= CURRENT_DATE AND " +
            "   p.minAmount <= :amount AND " +
            "   p.status = true AND p.type = true")
    List<Promotion> findByInvoiceAmount(@Param("amount") Double amount);

    @Query("SELECT p " +
            "FROM Promotion p " +
            "LEFT JOIN RoomTypePromotion rtp ON p.id = rtp.promotion.id " +
            "LEFT JOIN RoomType rt ON rtp.roomType.id = rt.id " +
            "WHERE p.status = true AND p.type = false AND rt.code = :roomType")
    List<Promotion> findByRoomType(@Param("roomType") String roomType);

}

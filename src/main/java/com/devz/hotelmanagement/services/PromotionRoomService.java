package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.RoomTypePromotion;

import java.util.List;

public interface PromotionRoomService extends ServiceBase<RoomTypePromotion>{

    List<RoomTypePromotion> findByRoomTypeId(Integer roomId);

    void delete(Integer id);

    List<RoomTypePromotion> findAllCurrForRoomType();

}

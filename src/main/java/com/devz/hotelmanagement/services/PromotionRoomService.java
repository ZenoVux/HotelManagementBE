package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.PromotionRoom;

import java.util.List;

public interface PromotionRoomService extends ServiceBase<PromotionRoom>{
    List<PromotionRoom> findByRoomId(Integer roomId);

    void delete(Integer id);
}

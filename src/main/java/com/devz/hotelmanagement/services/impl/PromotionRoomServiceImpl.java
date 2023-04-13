package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.RoomTypePromotion;
import com.devz.hotelmanagement.repositories.RoomTypePromotionRepository;
import com.devz.hotelmanagement.services.PromotionRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromotionRoomServiceImpl implements PromotionRoomService {
    @Autowired
    RoomTypePromotionRepository roomTypePromotionRepository;

    @Override
    public List<RoomTypePromotion> findByRoomTypeId(Integer roomTypeId) {
        return roomTypePromotionRepository.findByRoomTypeId(roomTypeId);
    }

    @Override
    public void delete(Integer id) {
    	roomTypePromotionRepository.deleteById(id);
    }

    @Override
    public List<RoomTypePromotion> findAll() {
        return roomTypePromotionRepository.findAll();
    }

    @Override
    public RoomTypePromotion findById(int id) {
    	 Optional<RoomTypePromotion> optional = roomTypePromotionRepository.findById(id);
         if (optional.isPresent()) {
             return optional.get();
         }
         return null;
    }

    @Override
    public RoomTypePromotion findByCode(String code) {
        return null;
    }

    @Override
    public RoomTypePromotion create(RoomTypePromotion entity) {
    	entity.setId(null);
    	return roomTypePromotionRepository.save(entity);
    }

    @Override
    public RoomTypePromotion update(RoomTypePromotion entity) {
    	return roomTypePromotionRepository.save(entity);
    }
}

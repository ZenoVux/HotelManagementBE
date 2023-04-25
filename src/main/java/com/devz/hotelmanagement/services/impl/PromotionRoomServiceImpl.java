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
    RoomTypePromotionRepository roomTypePromotionRepo;

    @Override
    public List<RoomTypePromotion> findByRoomTypeId(Integer roomTypeId) {
        return roomTypePromotionRepo.findByRoomTypeId(roomTypeId);
    }

    @Override
    public void delete(Integer id) {
    	roomTypePromotionRepo.deleteById(id);
    }

    @Override
    public List<RoomTypePromotion> findAllCurrForRoomType() {
        return roomTypePromotionRepo.findAllCurrForRoomType();
    }

    @Override
    public RoomTypePromotion findCurrByRoomTypeCode(String code) {
        Optional<RoomTypePromotion> optional = roomTypePromotionRepo.findCurrByRoomTypeCode(code);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public List<RoomTypePromotion> findAll() {
        return roomTypePromotionRepo.findAll();
    }

    @Override
    public RoomTypePromotion findById(int id) {
    	 Optional<RoomTypePromotion> optional = roomTypePromotionRepo.findById(id);
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
    	return roomTypePromotionRepo.save(entity);
    }

    @Override
    public RoomTypePromotion update(RoomTypePromotion entity) {
    	return roomTypePromotionRepo.save(entity);
    }
}

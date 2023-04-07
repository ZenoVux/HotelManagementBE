package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.PromotionRoom;
import com.devz.hotelmanagement.repositories.PromotionRoomRepository;
import com.devz.hotelmanagement.services.PromotionRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromotionRoomServiceImpl implements PromotionRoomService {
    @Autowired
    PromotionRoomRepository promotionRoomRepository ;

    @Override
    public List<PromotionRoom> findByRoomId(Integer roomId) {
        return promotionRoomRepository.findByRoomId(roomId);
    }

    @Override
    public void delete(Integer id) {
    	promotionRoomRepository.deleteById(id);
    }

    @Override
    public List<PromotionRoom> findAll() {
        return promotionRoomRepository.findAll();
    }

    @Override
    public PromotionRoom findById(int id) {
    	 Optional<PromotionRoom> optional = promotionRoomRepository.findById(id);
         if (optional.isPresent()) {
             return optional.get();
         }
         return null;
    }

    @Override
    public PromotionRoom findByCode(String code) {
        return null;
    }

    @Override
    public PromotionRoom create(PromotionRoom entity) {
    	entity.setId(null);
    	return promotionRoomRepository.save(entity);
    }

    @Override
    public PromotionRoom update(PromotionRoom entity) {
    	return promotionRoomRepository.save(entity);
    }
}

package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.RoomType;
import com.devz.hotelmanagement.repositories.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devz.hotelmanagement.services.RoomTypeService;

import java.util.List;
import java.util.Optional;

@Service
public class RoomTypeServiceImpl implements RoomTypeService {

    @Autowired
    private RoomTypeRepository roomTypeRepo;

    @Override
    public List<RoomType> findAll() {
        return roomTypeRepo.findAll();
    }

    @Override
    public RoomType findById(int id) {
        Optional<RoomType> optional = roomTypeRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public RoomType findByCode(String code) {
        return null;
    }

    @Override
    public RoomType create(RoomType roomType) {
        roomType.setId(null);
        return roomTypeRepo.save(roomType);
    }

    @Override
    public RoomType update(RoomType roomType) {
        return roomTypeRepo.save(roomType);
    }
}

package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.RoomImage;
import com.devz.hotelmanagement.repositories.RoomImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devz.hotelmanagement.services.RoomImageService;

import java.util.List;
import java.util.Optional;

@Service
public class RoomImageServiceImpl implements RoomImageService {

    @Autowired
    private RoomImageRepository roomImageRepo;

    @Override
    public List<RoomImage> findAll() {
        return roomImageRepo.findAll();
    }

    @Override
    public RoomImage findById(int id) {
        Optional<RoomImage> optional = roomImageRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public RoomImage create(RoomImage roomImage) {
        roomImage.setId(null);
        return roomImageRepo.save(roomImage);
    }

    @Override
    public RoomImage update(RoomImage roomImage) {
        return roomImageRepo.save(roomImage);
    }
}

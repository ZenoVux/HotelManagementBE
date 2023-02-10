package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.RoomDetail;
import com.devz.hotelmanagement.repositories.RoomDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devz.hotelmanagement.services.RoomDetailService;

import java.util.List;
import java.util.Optional;

@Service
public class RoomDetailServiceImpl implements RoomDetailService {

    @Autowired
    private RoomDetailRepository roomDetailRepo;

    @Override
    public List<RoomDetail> findAll() {
        return roomDetailRepo.findAll();
    }

    @Override
    public RoomDetail findById(int id) {
        Optional<RoomDetail> optional = roomDetailRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public RoomDetail create(RoomDetail roomDetail) {
        roomDetail.setId(null);
        return roomDetailRepo.save(roomDetail);
    }

    @Override
    public RoomDetail update(RoomDetail roomDetail) {
        return roomDetailRepo.save(roomDetail);
    }
}

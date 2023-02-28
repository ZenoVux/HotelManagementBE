package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.BedRoom;
import com.devz.hotelmanagement.repositories.BedRoomRepository;
import com.devz.hotelmanagement.services.BedRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BedRoomServiceImpl implements BedRoomService {

    @Autowired
    private BedRoomRepository bedRoomRepo;

    @Override
    public List<BedRoom> findAll() {
        return bedRoomRepo.findAll();
    }

    @Override
    public BedRoom findById(int id) {
        Optional<BedRoom> optional = bedRoomRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public BedRoom findByCode(String code) {
        return null;
    }

    @Override
    public BedRoom create(BedRoom bedRoom) {
        bedRoom.setId(null);
        return bedRoomRepo.save(bedRoom);
    }

    @Override
    public BedRoom update(BedRoom bedRoom) {
        return bedRoomRepo.save(bedRoom);
    }

    public List<BedRoom> getBedRoomsByRoomId(Integer id){
        return bedRoomRepo.getBedRoomsByRoomId(id);
    }

}

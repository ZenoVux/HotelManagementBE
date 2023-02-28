package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.Floor;
import com.devz.hotelmanagement.repositories.FloorRepository;
import com.devz.hotelmanagement.services.FloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FloorServiceImpl implements FloorService {

    @Autowired
    private FloorRepository floorRepo;

    @Override
    public List<Floor> findAll() {
        return floorRepo.findAll();
    }

    @Override
    public Floor findById(int id) {
        Optional<Floor> optional = floorRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public Floor findByCode(String code) {
        return null;
    }

    @Override
    public Floor create(Floor floor) {
        floor.setId(null);
        return floorRepo.save(floor);
    }

    @Override
    public Floor update(Floor floor) {
        return floorRepo.save(floor);
    }
}

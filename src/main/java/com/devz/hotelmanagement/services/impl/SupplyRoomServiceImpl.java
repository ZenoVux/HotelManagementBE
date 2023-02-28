package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.SupplyRoom;
import com.devz.hotelmanagement.repositories.SupplyRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devz.hotelmanagement.services.SupplyRoomService;

import java.util.List;
import java.util.Optional;

@Service
public class SupplyRoomServiceImpl implements SupplyRoomService {

    @Autowired
    private SupplyRoomRepository supplyRoomRepo;

    @Override
    public List<SupplyRoom> findAll() {
        return supplyRoomRepo.findAll();
    }

    @Override
    public SupplyRoom findById(int id) {
        Optional<SupplyRoom> optional = supplyRoomRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public SupplyRoom findByCode(String code) {
    	return null;
    }

    @Override
    public SupplyRoom create(SupplyRoom supplyRoom) {
        supplyRoom.setId(null);
        return supplyRoomRepo.save(supplyRoom);
    }

    @Override
    public SupplyRoom update(SupplyRoom supplyRoom) {
        return supplyRoomRepo.save(supplyRoom);
    }

	@Override
	public List<SupplyRoom> findByCodeRoom(String code) {
		return supplyRoomRepo.findByCodeRoom(code);
	}

}

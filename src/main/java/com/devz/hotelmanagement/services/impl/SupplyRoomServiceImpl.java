package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.SupplyRoomType;
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
    public List<SupplyRoomType> findAll() {
        return supplyRoomRepo.findAll();
    }

    @Override
    public SupplyRoomType findById(int id) {
        Optional<SupplyRoomType> optional = supplyRoomRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public SupplyRoomType findByCode(String code) {
    	return null;
    }

    @Override
    public SupplyRoomType create(SupplyRoomType supplyRoomType) {
        supplyRoomType.setId(null);
        return supplyRoomRepo.save(supplyRoomType);
    }

    @Override
    public SupplyRoomType update(SupplyRoomType supplyRoomType) {
        return supplyRoomRepo.save(supplyRoomType);
    }

	@Override
	public List<SupplyRoomType> findByCodeRoom(String code) {
		return supplyRoomRepo.findByCodeRoom(code);
	}

	@Override
	public void deleteById(Integer id) {
		supplyRoomRepo.deleteById(id);
		
	}

}

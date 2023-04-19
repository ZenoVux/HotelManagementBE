package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.RoomTypeImage;
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
    public List<RoomTypeImage> findAll() {
        return roomImageRepo.findAll();
    }

    @Override
    public RoomTypeImage findById(int id) {
        Optional<RoomTypeImage> optional = roomImageRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public RoomTypeImage findByCode(String code) {
        return null;
    }	

    @Override
    public RoomTypeImage create(RoomTypeImage roomTypeImage) {
        roomTypeImage.setId(null);
        return roomImageRepo.save(roomTypeImage);
    }

    @Override
    public RoomTypeImage update(RoomTypeImage roomTypeImage) {
        return roomImageRepo.save(roomTypeImage);
    }

	@Override
	public List<RoomTypeImage> getListByCodeRoom(String codeRoom) {
		return roomImageRepo.getListByCodeRoom(codeRoom);
	}

	@Override
	public void deleteById(Integer id) {
		roomImageRepo.deleteById(id);;
	}
}

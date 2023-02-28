package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.BedRoom;
import com.devz.hotelmanagement.entities.Room;
import com.devz.hotelmanagement.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devz.hotelmanagement.services.RoomService;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepo;

    @Override
    public List<Room> findAll() {
        return roomRepo.findAll();
    }

    @Override
    public Room findById(int id) {
        Optional<Room> optional = roomRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }
 
    @Override
    public Room findByCode(String code) {
    	Room room = roomRepo.findByCode(code);
        return room;
    }

    @Override
    public Room create(Room room) {
        room.setId(null);
        return roomRepo.save(room);
    }

    @Override
    public Room update(Room room) {
        return roomRepo.save(room);
    }

    public List<Room> getRoomBookings(String roomTypeCode){
        return roomRepo.getRoomBookings(roomTypeCode);
    }

}

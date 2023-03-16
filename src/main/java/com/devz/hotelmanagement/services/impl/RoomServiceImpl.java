package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.Room;
import com.devz.hotelmanagement.models.HotelRoom;
import com.devz.hotelmanagement.models.RoomStatus;
import com.devz.hotelmanagement.models.RoomStatusCount;
import com.devz.hotelmanagement.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devz.hotelmanagement.services.RoomService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<RoomStatusCount> getStatusCount() {
        return roomRepo.getStatusCount().stream()
                .map(item -> new RoomStatusCount(((Long) item[0]).intValue(), (Long) item[1]))
                .collect(Collectors.toList());
    }

    @Override
    public List<Room> getByFloorId(int id) {
        return roomRepo.getByFloorId(id);
    }

    @Override
    public List<HotelRoom> getHotelRoom() {
        return roomRepo.getHotelRoom().stream()
                .map(item -> new HotelRoom(item[0].toString(), item[1].toString(), (String) item[2], (Date) item[3], (Date) item[4], ((Long) item[5]).intValue()))
                .collect(Collectors.toList());
    }

    @Override
    public void updateStatus(RoomStatus roomStatus) {
        roomRepo.updateRoomStatusByCode(roomStatus.getCode(), roomStatus.getStatus());
    }
}

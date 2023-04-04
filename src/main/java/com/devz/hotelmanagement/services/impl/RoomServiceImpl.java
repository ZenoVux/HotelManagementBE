package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.*;
import com.devz.hotelmanagement.models.StatusCountResp;
import com.devz.hotelmanagement.repositories.RoomRepository;
import com.devz.hotelmanagement.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Room> getByFloorId(int id) {
        return roomRepo.getByFloorId(id);
    }

    public List<Room> findByIds(List<Integer> roomIds) {
        return roomRepo.findAllById(roomIds);
    }

    @Override
    public List<Room> findAllByCodeASC() {
        return roomRepo.findAllByCodeASC();
    }

    @Override
    public List<Room> findUnbookedRoomsByCheckinAndCheckout(Date checkin, Date checkout) {
        return roomRepo.findUnbookedRoomsByCheckinAndCheckout(checkin, checkout);
    }

}

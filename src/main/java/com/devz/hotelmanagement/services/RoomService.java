package com.devz.hotelmanagement.services;

import java.util.List;

import com.devz.hotelmanagement.entities.Room;

public interface RoomService {
    List<Room> findAll();

    Room findById(int id);

    Room create(Room room);

    Room update(Room room);
}

package com.devz.hotelmanagement.services;

import java.util.List;

import com.devz.hotelmanagement.entities.RoomType;

public interface RoomTypeService {
    List<RoomType> findAll();

    RoomType findById(int id);

    RoomType create(RoomType roomType);

    RoomType update(RoomType roomType);
}

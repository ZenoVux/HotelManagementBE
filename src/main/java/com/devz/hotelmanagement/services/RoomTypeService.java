package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.RoomType;

public interface RoomTypeService extends ServiceBase<RoomType> {

    RoomType getRoomTypeByCode(String code);

}

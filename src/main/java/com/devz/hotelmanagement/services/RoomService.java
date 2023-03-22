package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.Room;
import com.devz.hotelmanagement.models.HotelRoom;
import com.devz.hotelmanagement.models.StatusCount;

import java.util.List;

public interface RoomService extends ServiceBase<Room> {

    List<StatusCount> getStatusCount();

    List<Room> getByFloorId(int id);

    List<HotelRoom> getHotelRoom();

    void updateStatus(String code, Integer status);

}

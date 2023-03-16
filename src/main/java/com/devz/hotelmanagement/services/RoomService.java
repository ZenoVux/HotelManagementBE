package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.Room;
import com.devz.hotelmanagement.models.HotelRoom;
import com.devz.hotelmanagement.models.RoomStatus;
import com.devz.hotelmanagement.models.RoomStatusCount;

import java.util.List;

public interface RoomService extends ServiceBase<Room> {

    List<RoomStatusCount> getStatusCount();

    List<Room> getByFloorId(int id);

    List<HotelRoom> getHotelRoom();

    void updateStatus(RoomStatus roomStatus);
}

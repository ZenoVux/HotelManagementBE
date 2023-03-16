package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.Room;
import com.devz.hotelmanagement.models.RoomStatusCount;

import java.util.List;

public interface RoomService extends ServiceBase<Room> {

    List<Room> getRoomBookings(String roomTypeCode);

    List<RoomStatusCount> getStatusCount();

    List<Room> getByFloorId(int id);
  
}

package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.BedRoom;
import com.devz.hotelmanagement.entities.Room;

import java.util.List;

public interface RoomService extends ServiceBase<Room> {

    List<Room> getRoomBookings(String roomTypeCode);

}

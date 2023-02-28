package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.BedRoom;

import java.util.List;

public interface BedRoomService extends ServiceBase<BedRoom> {

    List<BedRoom> getBedRoomsByRoomId(Integer id);

}

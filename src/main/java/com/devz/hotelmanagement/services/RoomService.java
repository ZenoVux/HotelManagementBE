package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.Room;
import com.devz.hotelmanagement.models.StatusCountResp;

import java.util.Date;
import java.util.List;

public interface RoomService extends ServiceBase<Room> {

    List<Room> getByFloorId(int id);

    List<Room> findByIds(List<Integer> roomIds);

    List<Room> findAllByCodeASC();

    List<Room> findUnbookedRoomsByCheckinAndCheckout(Date checkin, Date checkout);

    String getMaxCode(Integer floor_Id);

    List<Room> updateAll(List<Room> rooms);

}

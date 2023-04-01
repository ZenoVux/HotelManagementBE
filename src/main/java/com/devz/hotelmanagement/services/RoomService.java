package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.Room;
import com.devz.hotelmanagement.models.StatusCountResp;

import java.util.Date;
import java.util.List;

public interface RoomService extends ServiceBase<Room> {


    List<StatusCountResp> getStatusCount();

    List<Room> getByFloorId(int id);

    void updateStatus(String code, Integer status);

    List<Room> findByIds(List<Integer> roomIds);

    List<Room> findAllByCodeASC();

    List<Room> findUnbookedRoomsByCheckinAndCheckout(Date checkin, Date checkout);

}

package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.ServiceRoom;

import java.util.List;

public interface ServiceRoomService extends ServiceBase<ServiceRoom> {

    List<ServiceRoom> findByStatus(Boolean status);
}

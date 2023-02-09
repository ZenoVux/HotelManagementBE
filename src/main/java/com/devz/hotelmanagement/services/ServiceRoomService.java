package com.devz.hotelmanagement.services;

import java.util.List;

import com.devz.hotelmanagement.entities.ServiceRoom;

public interface ServiceRoomService {
    List<ServiceRoom> findAll();

    ServiceRoom findById(int id);

    ServiceRoom create(ServiceRoom serviceRoom);

    ServiceRoom update(ServiceRoom serviceRoom);
}

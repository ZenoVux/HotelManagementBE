package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.ServiceRoom;
import com.devz.hotelmanagement.repositories.ServiceRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devz.hotelmanagement.services.ServiceRoomService;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceRoomServiceImpl implements ServiceRoomService {

    @Autowired
    ServiceRoomRepository serviceRoomRepository;

    @Override
    public List<ServiceRoom> findAll() {
        return serviceRoomRepository.findAll();
    }

    @Override
    public ServiceRoom findById(int id) {
        Optional<ServiceRoom> optional = serviceRoomRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public ServiceRoom findByCode(String code) {
        return null;
    }

    @Override
    public ServiceRoom create(ServiceRoom serviceRoom) {
        serviceRoom.setId(null);
        serviceRoomRepository.save(serviceRoom);
        return null;
    }

    @Override
    public ServiceRoom update(ServiceRoom serviceRoom) {
        return serviceRoomRepository.save(serviceRoom);
    }
}

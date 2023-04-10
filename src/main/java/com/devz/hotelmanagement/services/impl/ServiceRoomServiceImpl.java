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
    ServiceRoomRepository serviceRoomRepo;

    @Override
    public List<ServiceRoom> findAll() {
        return serviceRoomRepo.findAll();
    }

    @Override
    public ServiceRoom findById(int id) {
        Optional<ServiceRoom> optional = serviceRoomRepo.findById(id);
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
        serviceRoomRepo.save(serviceRoom);
        return null;
    }

    @Override
    public ServiceRoom update(ServiceRoom serviceRoom) {
        return serviceRoomRepo.save(serviceRoom);
    }

    @Override
    public List<ServiceRoom> findByStatus(Boolean status) {
        return serviceRoomRepo.findByStatus(status);
    }
}

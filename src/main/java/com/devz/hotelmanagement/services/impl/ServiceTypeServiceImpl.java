package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.ServiceType;
import com.devz.hotelmanagement.repositories.ServiceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devz.hotelmanagement.services.ServiceTypeService;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceTypeServiceImpl implements ServiceTypeService {

    @Autowired
    ServiceTypeRepository serviceTypeRepository;

    @Override
    public List<ServiceType> findAll() {
        return serviceTypeRepository.findAll();
    }

    @Override
    public ServiceType findById(int id) {
        Optional<ServiceType> optional = serviceTypeRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public ServiceType create(ServiceType serviceType) {
        serviceType.setId(null);
        serviceTypeRepository.save(serviceType);
        return null;
    }

    @Override
    public ServiceType update(ServiceType serviceType) {
        return serviceTypeRepository.save(serviceType);
    }
}

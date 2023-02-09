package com.devz.hotelmanagement.services;

import java.util.List;

import com.devz.hotelmanagement.entities.ServiceType;

public interface ServiceTypeService {
    List<ServiceType> findAll();

    ServiceType findById(int id);

    ServiceType create(ServiceType serviceType);

    ServiceType update(ServiceType serviceType);
}

package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.HostedAt;
import com.devz.hotelmanagement.entities.UsedService;
import com.devz.hotelmanagement.repositories.UsedServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devz.hotelmanagement.services.UsedServiceService;

import java.util.List;
import java.util.Optional;

@Service
public class UsedServiceServiceImpl implements UsedServiceService {

    @Autowired
    UsedServiceRepository usedServiceRepository;

    @Override
    public List<UsedService> findAll() {
        return usedServiceRepository.findAll();
    }

    @Override
    public UsedService findById(int id) {
        Optional<UsedService> optional = usedServiceRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public UsedService findByCode(String code) {
        return null;
    }

    @Override
    public UsedService create(UsedService usedService) {
        usedService.setId(null);
        return usedServiceRepository.save(usedService);
    }

    @Override
    public UsedService update(UsedService usedService) {
        return usedServiceRepository.save(usedService);
    }

    @Override
    public List<UsedService> findAllByBookingDetailCode(String code) {
        return usedServiceRepository.findAllByBookingDetailCode(code);
    }

    @Override
    public List<UsedService> findByInvoiceDetailId(Integer id) {
        return usedServiceRepository.findAllByInvoiceDetailId(id);
    }

    @Override
    public void delete(Integer id) {
        usedServiceRepository.deleteById(id);
    }

    @Override
    public void updateAll(List<UsedService> usedServices) {
        usedServiceRepository.saveAll(usedServices);
    }

    @Override
    public List<UsedService> findAllByBookingDetailId(Integer id) {
        return usedServiceRepository.findAllByBookingDetailId(id);
    }
}

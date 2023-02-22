package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.SupplyType;
import com.devz.hotelmanagement.repositories.SupplyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devz.hotelmanagement.services.SupplyTypeService;

import java.util.List;
import java.util.Optional;

@Service
public class SupplyTypeServiceImpl implements SupplyTypeService {

    @Autowired
    SupplyTypeRepository supplyTypeRepository;

    @Override
    public List<SupplyType> findAll() {
        return supplyTypeRepository.findAll();
    }

    @Override
    public SupplyType findById(int id) {
        Optional<SupplyType> optional = supplyTypeRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public SupplyType findByCode(String code) {
        return null;
    }

    @Override
    public SupplyType create(SupplyType supplyType) {
        supplyType.setId(null);
        return supplyTypeRepository.save(supplyType);
    }

    @Override
    public SupplyType update(SupplyType supplyType) {
        return supplyTypeRepository.save(supplyType);
    }
}

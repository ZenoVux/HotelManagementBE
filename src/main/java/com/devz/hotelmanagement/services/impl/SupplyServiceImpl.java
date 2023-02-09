package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.Supply;
import com.devz.hotelmanagement.repositories.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devz.hotelmanagement.services.SupplyService;

import java.util.List;
import java.util.Optional;

@Service
public class SupplyServiceImpl implements SupplyService {

    @Autowired
    SupplyRepository supplyRepository;

    @Override
    public List<Supply> findAll() {
        return supplyRepository.findAll();
    }

    @Override
    public Supply findById(int id) {
        Optional<Supply> optional = supplyRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public Supply create(Supply supply) {
        supply.setId(null);
        return supplyRepository.save(supply);
    }

    @Override
    public Supply update(Supply supply) {
        return supplyRepository.save(supply);
    }
}

package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.Shift;
import com.devz.hotelmanagement.repositories.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devz.hotelmanagement.services.ShiftService;

import java.util.List;
import java.util.Optional;

@Service
public class ShiftServiceImpl implements ShiftService {

    @Autowired
    ShiftRepository shiftRepository;

    @Override
    public List<Shift> findAll() {
        return shiftRepository.findAll();
    }

    @Override
    public Shift findById(int id) {
        Optional<Shift> optional = shiftRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public Shift create(Shift shift) {
        shift.setId(null);
        return shiftRepository.save(shift);
    }

    @Override
    public Shift update(Shift shift) {
        return shiftRepository.save(shift);
    }
}

package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.BedType;
import com.devz.hotelmanagement.repositories.BedTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devz.hotelmanagement.services.BedTypeService;

import java.util.List;
import java.util.Optional;

@Service
public class BedTypeServiceImpl implements BedTypeService {

    @Autowired
    private BedTypeRepository bedTypeRepo;

    @Override
    public List<BedType> findAll() {
        return bedTypeRepo.findAll();
    }

    @Override
    public BedType findById(int id) {
        Optional<BedType> optional = bedTypeRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public BedType create(BedType bedType) {
        bedType.setId(null);
        return bedTypeRepo.save(bedType);
    }

    @Override
    public BedType upadte(BedType bedType) {
        return bedTypeRepo.save(bedType);
    }
}

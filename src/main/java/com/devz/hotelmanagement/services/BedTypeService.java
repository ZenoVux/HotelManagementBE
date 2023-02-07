package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.Account;
import com.devz.hotelmanagement.entities.BedType;

import java.util.List;

public interface BedTypeService {

    List<BedType> findAll();

    BedType findById(int id);

    BedType create(BedType bedType);

    BedType upadte(BedType bedType);
}

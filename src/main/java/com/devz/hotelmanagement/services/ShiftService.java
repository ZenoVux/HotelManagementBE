package com.devz.hotelmanagement.services;

import java.util.List;

import com.devz.hotelmanagement.entities.Shift;


public interface ShiftService {
    List<Shift> findAll();

    Shift findById(int id);

    Shift create(Shift shift);

    Shift update(Shift shift);
}

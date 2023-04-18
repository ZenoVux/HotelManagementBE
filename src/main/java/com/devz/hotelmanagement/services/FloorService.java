package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.Floor;

public interface FloorService extends ServiceBase<Floor> {
    void delete(Integer id);
}

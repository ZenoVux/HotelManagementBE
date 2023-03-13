package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.UsedService;

import java.util.List;

public interface UsedServiceService extends ServiceBase<UsedService> {

    List<UsedService> findAllByBookingDetailId(Integer id);

    void delete(Integer id);
}

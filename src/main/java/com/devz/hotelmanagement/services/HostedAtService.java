package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.HostedAt;

import java.util.List;

public interface HostedAtService extends ServiceBase<HostedAt> {

    List<HostedAt> findAllByBookingDetailId(Integer id);
}

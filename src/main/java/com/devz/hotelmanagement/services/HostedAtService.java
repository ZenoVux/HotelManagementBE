package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.HostedAt;

import java.util.List;

public interface HostedAtService extends ServiceBase<HostedAt> {

    List<HostedAt> findByBookingDetailId(Integer id);

    void delete(Integer id);

    List<HostedAt> updateAll(List<HostedAt> hostedAts);

    List<HostedAt> findByInvoiceDetailId(Integer id);
}

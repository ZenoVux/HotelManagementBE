package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.HostedAt;

import java.util.List;

public interface HostedAtService extends ServiceBase<HostedAt> {

    void delete(Integer id);

    List<HostedAt> updateOrSaveAll(List<HostedAt> hostedAts);

    List<HostedAt> findByInvoiceDetailId(Integer id);
}

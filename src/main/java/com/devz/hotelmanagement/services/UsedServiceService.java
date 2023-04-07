package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.UsedService;

import java.util.List;

public interface UsedServiceService extends ServiceBase<UsedService> {

    List<UsedService> findByInvoiceDetailId(Integer invoiceDetailId);

    List<UsedService> findAllByInvoiceDetailIdAndStatus(Integer invoiceDetailId, Boolean status);

    void delete(Integer id);

    void stop(Integer id);

    List<UsedService> updateAll(List<UsedService> usedServices);

    UsedService findByServiceRoomIdAndInvoiceDetailIdAndStatus(Integer serviceRoomId, Integer invoiceDetailId, Boolean status);
}

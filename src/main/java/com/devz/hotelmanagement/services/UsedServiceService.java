package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.UsedService;

import java.util.List;

public interface UsedServiceService extends ServiceBase<UsedService> {

    List<UsedService> findByInvoiceDetailId(Integer invoiceDetailId);

    List<UsedService> findAllByInvoiceDetailIdAndIsUsed(Integer invoiceDetailId, Boolean isUsed);

    void delete(Integer id);

    List<UsedService> updateAll(List<UsedService> usedServices);

    UsedService findByServiceRoomIdAndInvoiceDetailIdAndIsUsed(Integer serviceRoomId, Integer invoiceDetailId, Boolean isUsed);
}

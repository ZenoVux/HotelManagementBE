package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.UsedService;

import java.util.List;

public interface UsedServiceService extends ServiceBase<UsedService> {

    List<UsedService> findAllByBookingDetailCode(String code);

    List<UsedService> findByInvoiceDetailId(Integer id);

    void delete(Integer id);

    List<UsedService> updateAll(List<UsedService> usedServices);

    List<UsedService> findAllByBookingDetailId(Integer id);

    UsedService findByServiceRoomIdAndInvoiceDetailId(Integer serviceRoomId, Integer invoiceDetailId);
}

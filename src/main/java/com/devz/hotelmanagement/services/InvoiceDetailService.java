package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.InvoiceDetail;

import java.util.List;

public interface InvoiceDetailService extends ServiceBase<InvoiceDetail> {

    InvoiceDetail findUsingByRoomCode(String code);

    List<InvoiceDetail> findByInvoiceCode(String code);

    void updateAll(List<InvoiceDetail> invoiceDetails);

    List<InvoiceDetail> findAllUsing();

}

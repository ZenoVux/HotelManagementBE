package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.InvoiceDetail;

import java.util.List;

public interface InvoiceDetailService extends ServiceBase<InvoiceDetail> {

    InvoiceDetail findUsingByRoomCode(String code);

    List<InvoiceDetail> findByInvoiceCode(String code);

    List<InvoiceDetail> updateOrSaveAll(List<InvoiceDetail> invoiceDetails);

    List<InvoiceDetail> findAllUsing();

    List<InvoiceDetail> findByInvoiceCodeAndStatus(String code, Integer status);

}

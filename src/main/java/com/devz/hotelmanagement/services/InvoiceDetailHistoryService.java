package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.InvoiceDetailHistory;

import java.util.List;

public interface InvoiceDetailHistoryService extends ServiceBase<InvoiceDetailHistory> {

    List<InvoiceDetailHistory> findAllByInvoiceDetailId(Integer invoiceDetailId);

}

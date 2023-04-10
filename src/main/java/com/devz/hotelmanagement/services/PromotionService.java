package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.Promotion;

import java.util.List;

public interface PromotionService extends ServiceBase<Promotion> {

    Promotion findByCode(String code);

    List<Promotion> findByInvoiceAmount(Double amount);

}

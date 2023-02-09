package com.devz.hotelmanagement.services;

import java.util.List;

import com.devz.hotelmanagement.entities.Promotion;

public interface PromotionService {
    List<Promotion> findAll();

    Promotion findById(int id);

    Promotion create(Promotion promotion);

    Promotion update(Promotion promotion);
}

package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.Promotion;
import com.devz.hotelmanagement.services.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/promotions")
public class PromotionRestController {

    @Autowired
    private PromotionService promotionService;

    @GetMapping
    public List<Promotion> getAll() {
        return promotionService.findAll();
    }

    @PostMapping
    public Promotion create(@RequestBody Promotion promotion) {
        return promotionService.create(promotion);
    }

    @PutMapping
    public Promotion update(@RequestBody Promotion promotion) {
        return promotionService.update(promotion);
    }
}

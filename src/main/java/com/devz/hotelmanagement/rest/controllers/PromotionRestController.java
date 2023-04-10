package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.Promotion;
import com.devz.hotelmanagement.services.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    	promotion.setStatus(true);
        return promotionService.create(promotion);
    }

    @PutMapping
    public Promotion update(@RequestBody Promotion promotion) {
        return promotionService.update(promotion);
    }

    @GetMapping("/by-code")
    public ResponseEntity<Promotion> findByAmount(@RequestParam("code") String code) {
        Promotion promotion = promotionService.findByCode(code);
        if (promotion != null) {
            return ResponseEntity.ok(promotion);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/by-invoice-amount")
    public List<Promotion> findByInvoiceAmount(@RequestParam("amount") Double amount) {
        return promotionService.findByInvoiceAmount(amount);
    }
}

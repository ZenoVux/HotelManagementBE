package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.Promotion;
import com.devz.hotelmanagement.repositories.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.devz.hotelmanagement.services.PromotionService;

import jakarta.annotation.PostConstruct;

import java.util.List;
import java.util.Optional;

@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionRepository promotionRepo;

    @Override
    public List<Promotion> findAll() {
        return promotionRepo.findAll();
    }

    @Override
    public Promotion findById(int id) {
        Optional<Promotion> optional = promotionRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public Promotion findByCode(String code) {
        Optional<Promotion> optional = promotionRepo.findByCode(code);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public List<Promotion> findByAmount(Double amount) {
        return promotionRepo.findByAmount(amount);
    }

    @Override
    public Promotion create(Promotion promotion) {
        promotion.setId(null);
        return promotionRepo.save(promotion);
    }

    @Override
    public Promotion update(Promotion promotion) {
        return promotionRepo.save(promotion);
    }
    
//    @PostConstruct // chạy khi app khởi động
//    @Scheduled(cron = "0 */5 * * * ?")// chạy vào mỗi 5
    public void updatePromotionStatus() {
        List<Promotion> promotions = promotionRepo.findAll();
        for (Promotion promotion : promotions) {
            promotion.updateStatus();
            promotionRepo.save(promotion);
        }
    }
}

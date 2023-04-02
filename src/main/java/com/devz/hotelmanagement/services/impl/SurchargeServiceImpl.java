package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.Surcharge;
import com.devz.hotelmanagement.repositories.SurchargeRepository;
import com.devz.hotelmanagement.services.SurchargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Service
public class SurchargeServiceImpl implements SurchargeService {

    @Autowired
    private SurchargeRepository surchargeRepo;

    @Override
    public List<Surcharge> findEarlyCheckinSurcharges() {
        return surchargeRepo.findEarlyCheckinSurcharges();
    }

    @Override
    public List<Surcharge> findLateCheckoutSurcharges() {
        return surchargeRepo.findLateCheckoutSurcharges();
    }

    @Override
    public Surcharge findCurEarlyCheckinSurcharge() {
        List<Surcharge> surcharges = surchargeRepo.findEarlyCheckinSurcharges();
        return findSurcharge(surcharges);
    }

    @Override
    public Surcharge findCurLateCheckoutSurcharge() {
        List<Surcharge> surcharges = surchargeRepo.findLateCheckoutSurcharges();
        return findSurcharge(surcharges);
    }

    private Surcharge findSurcharge(List<Surcharge> surcharges) {
        LocalTime currentTime = LocalTime.now(); // current time
        for (Surcharge surcharge : surcharges) {
            Date startDate = surcharge.getTimeStart();
            Date endDate = surcharge.getTimeEnd();
            LocalTime startTime = LocalTime.of(startDate.getHours(), startDate.getMinutes());
            LocalTime endTime = LocalTime.of(endDate.getHours(), endDate.getMinutes());
            System.out.println(startDate);
            if (currentTime.isAfter(startTime) && currentTime.isBefore(endTime)) {
                return surcharge;
            }
        }
        return null;
    }
}

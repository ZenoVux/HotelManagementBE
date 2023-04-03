package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.Surcharge;
import com.devz.hotelmanagement.repositories.SurchargeRepository;
import com.devz.hotelmanagement.services.SurchargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SurchargeServiceImpl implements SurchargeService {

    @Autowired
    private SurchargeRepository surchargeRepo;

    @Override
    public List<Surcharge> findAll() {
        return surchargeRepo.findAll();
    }

    @Override
    public Surcharge findById(int id) {
        Optional<Surcharge> optional = surchargeRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public Surcharge findByCode(String code) {
        Optional<Surcharge> optional = surchargeRepo.findByCode(code);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public Surcharge create(Surcharge surcharge) {
        surcharge.setId(null);
        try {
            String maxCode = surchargeRepo.getMaxCode();
            Integer index = 1;
            if (maxCode != null) {
                index = Integer.parseInt(maxCode.replace("S", ""));
                index++;
            }
            String code = String.format("S%05d", index);
            surcharge.setCode(code);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return surchargeRepo.save(surcharge);
    }

    @Override
    public Surcharge update(Surcharge surcharge) {
        return null;
    }

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

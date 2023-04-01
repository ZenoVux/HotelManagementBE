package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.Account;
import com.devz.hotelmanagement.entities.Surcharge;
import com.devz.hotelmanagement.services.SurchargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/surcharges")
public class SurchargeRestController {

    @Autowired
    private SurchargeService surchargeService;

    @GetMapping("/early-checkin")
    public List<Surcharge> findEarlyCheckinSurcharges() {
        return surchargeService.findEarlyCheckinSurcharges();
    }

    @GetMapping("/late-checkout")
    public List<Surcharge> findLateCheckoutSurcharges() {
        return surchargeService.findLateCheckoutSurcharges();
    }

    @GetMapping("/cur-early-checkin")
    public ResponseEntity<Surcharge> findCurEarlyCheckinSurcharge() {
        Surcharge surcharge = surchargeService.findCurEarlyCheckinSurcharge();
        if (surcharge != null) {
            return ResponseEntity.ok(surcharge);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/cur-late-checkout")
    public ResponseEntity<Surcharge> findCurLateCheckoutSurcharge() {
        Surcharge surcharge = surchargeService.findCurLateCheckoutSurcharge();
        if (surcharge != null) {
            return ResponseEntity.ok(surcharge);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}

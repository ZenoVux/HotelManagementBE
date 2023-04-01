package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.Surcharge;

import java.util.List;

public interface SurchargeService {

    List<Surcharge> findEarlyCheckinSurcharges();

    List<Surcharge> findLateCheckoutSurcharges();

    Surcharge findCurEarlyCheckinSurcharge();

    Surcharge findCurLateCheckoutSurcharge();

}

package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.PaymentMethod;

import java.util.List;

public interface PaymentMethodService extends ServiceBase<PaymentMethod> {

    List<PaymentMethod> findPaymentInUse();

    PaymentMethod findByCode(String code);

}

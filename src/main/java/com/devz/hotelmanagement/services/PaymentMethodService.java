package com.devz.hotelmanagement.services;

import java.util.List;

import com.devz.hotelmanagement.entities.PaymentMethod;

public interface PaymentMethodService {
	List<PaymentMethod> findAll();

	PaymentMethod findById(int id);

	PaymentMethod create(PaymentMethod paymentMethod);

	PaymentMethod update(PaymentMethod paymentMethod);
}

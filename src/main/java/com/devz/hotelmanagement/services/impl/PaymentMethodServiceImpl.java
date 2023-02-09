package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.PaymentMethod;
import com.devz.hotelmanagement.repositories.PaymentMethodRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devz.hotelmanagement.services.PaymentMethodService;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {
	@Autowired 
	PaymentMethodRepository paymentMethodRepo;
	
    @Override
    public List<PaymentMethod> findAll() {
        return paymentMethodRepo.findAll();
    }

    @Override
    public PaymentMethod findById(int id) {
    	Optional<PaymentMethod> optional = paymentMethodRepo.findById(id);
    	if (optional.isPresent()) {
			return optional.get();
		}
        return null;
    }

    @Override
    public PaymentMethod create(PaymentMethod paymentMethod) {
    	paymentMethod.setId(null);
        return paymentMethodRepo.save(paymentMethod);
    }

    @Override
    public PaymentMethod update(PaymentMethod paymentMethod) {
    	return paymentMethodRepo.save(paymentMethod);
    }
}

package com.devz.hotelmanagement.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devz.hotelmanagement.entities.Account;
import com.devz.hotelmanagement.entities.CustomerType;
import com.devz.hotelmanagement.repositories.CustomerTypeRepository;
import com.devz.hotelmanagement.services.CustomerTypeService;

@Service
public class CustomerTypeServiceImpl implements CustomerTypeService {
	
	@Autowired
	private CustomerTypeRepository customerTypeRepo;
	
	@Override
	public List<CustomerType> findAll() {
		 return customerTypeRepo.findAll();
	}

	@Override
	public CustomerType findById(int id) {
		Optional<CustomerType> optional = customerTypeRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
		return null;
	}

	@Override
	public CustomerType create(CustomerType customerType) {
		customerType.setId(null);
	        return customerTypeRepo.save(customerType);
	}

	@Override
	public CustomerType update(CustomerType customerType) {
		return customerTypeRepo.save(customerType);
	}

}

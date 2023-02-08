package com.devz.hotelmanagement.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devz.hotelmanagement.entities.Account;
import com.devz.hotelmanagement.entities.CustomerImage;
import com.devz.hotelmanagement.repositories.CustomerImageRepository;
import com.devz.hotelmanagement.services.CustomerImageService;

@Service
public class CustomerImageServiceImpl implements CustomerImageService {

	@Autowired
	private CustomerImageRepository customerImageRepo;
	
	@Override
	public List<CustomerImage> findAll() {
		return customerImageRepo.findAll();
	}

	@Override
	public CustomerImage findById(int id) {
		Optional<CustomerImage> optional = customerImageRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
		return null;
	}

	@Override
	public CustomerImage create(CustomerImage customerImage) {
		customerImage.setId(null);
        return customerImageRepo.save(customerImage);
	}

	@Override
	public CustomerImage update(CustomerImage customerImage) {
		return customerImageRepo.save(customerImage);
	}

}

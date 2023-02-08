package com.devz.hotelmanagement.services;

import java.util.List;

import com.devz.hotelmanagement.entities.Customer;

public interface CustomerService {
	List<Customer> findAll();

	Customer findById(int id);

	Customer create(Customer customer);

	Customer update(Customer customer);
}

package com.devz.hotelmanagement.services;

import java.util.List;

import com.devz.hotelmanagement.entities.CustomerType;

public interface CustomerTypeService {
	List<CustomerType> findAll();

	CustomerType findById(int id);

	CustomerType create(CustomerType customerType);

	CustomerType upadte(CustomerType customerType);
}

package com.devz.hotelmanagement.services;

import java.util.List;

import com.devz.hotelmanagement.entities.Service;

public interface ServiceService {
	List<Service> findAll();

	Service findById(int id);

	Service create(Service service);

	Service update(Service service);
}

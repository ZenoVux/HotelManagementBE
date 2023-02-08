package com.devz.hotelmanagement.services;

import java.util.List;

import com.devz.hotelmanagement.entities.UsedService;

public interface UsedServiceService {
	List<UsedService> findAll();

	UsedService findById(int id);

	UsedService create(UsedService usedService);

	UsedService update(UsedService usedService);
}

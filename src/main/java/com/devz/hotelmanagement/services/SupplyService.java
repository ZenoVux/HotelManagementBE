package com.devz.hotelmanagement.services;

import java.util.List;

import com.devz.hotelmanagement.entities.Supply;

public interface SupplyService {
	List<Supply> findAll();

	Supply findById(int id);

	Supply create(Supply supply);

	Supply update(Supply supply);
}

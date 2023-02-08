package com.devz.hotelmanagement.services;

import java.util.List;

import com.devz.hotelmanagement.entities.SupplyType;

public interface SupplyTypeService {
	List<SupplyType> findAll();

	SupplyType findById(int id);

	SupplyType create(SupplyType supplyType);

	SupplyType upadte(SupplyType supplyType);
}

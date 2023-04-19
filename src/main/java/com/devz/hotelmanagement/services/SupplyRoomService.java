package com.devz.hotelmanagement.services;

import java.util.List;

import com.devz.hotelmanagement.entities.SupplyRoomType;

public interface SupplyRoomService extends ServiceBase<SupplyRoomType> {
	List<SupplyRoomType> findByCodeRoom(String code);
	void deleteById(Integer id);
}

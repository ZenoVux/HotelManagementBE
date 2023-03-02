package com.devz.hotelmanagement.services;

import java.util.List;

import com.devz.hotelmanagement.entities.SupplyRoom;

public interface SupplyRoomService extends ServiceBase<SupplyRoom> {
	List<SupplyRoom> findByCodeRoom(String code);
	void deleteById(Integer id);
}

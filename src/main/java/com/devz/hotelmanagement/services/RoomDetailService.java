package com.devz.hotelmanagement.services;

import java.util.List;

import com.devz.hotelmanagement.entities.RoomDetail;

public interface RoomDetailService {
	List<RoomDetail> findAll();

	RoomDetail findById(int id);

	RoomDetail create(RoomDetail roomDetail);

	RoomDetail upadte(RoomDetail roomDetail);
}

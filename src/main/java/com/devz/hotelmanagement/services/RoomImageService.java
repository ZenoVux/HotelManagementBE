package com.devz.hotelmanagement.services;

import java.util.List;

import com.devz.hotelmanagement.entities.RoomImage;

public interface RoomImageService {
	List<RoomImage> findAll();

	RoomImage findById(int id);

	RoomImage create(RoomImage roomImage);

	RoomImage update(RoomImage roomImage);
}

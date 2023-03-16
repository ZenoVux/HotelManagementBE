package com.devz.hotelmanagement.services;

import java.util.List;

import com.devz.hotelmanagement.entities.RoomImage;

public interface RoomImageService extends ServiceBase<RoomImage> {
	List<RoomImage> getListByCodeRoom(String codeRoom);
	void deleteById(Integer id);
}

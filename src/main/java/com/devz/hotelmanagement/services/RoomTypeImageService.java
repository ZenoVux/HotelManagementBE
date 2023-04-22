package com.devz.hotelmanagement.services;

import java.util.List;

import com.devz.hotelmanagement.entities.RoomTypeImage;

public interface RoomTypeImageService extends ServiceBase<RoomTypeImage> {

	List<RoomTypeImage> getListByCodeRoom(String codeRoom);

	void deleteById(Integer id);

}

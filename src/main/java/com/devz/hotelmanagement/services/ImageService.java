package com.devz.hotelmanagement.services;

import java.util.List;

import com.devz.hotelmanagement.entities.Image;

public interface ImageService {
	List<Image> findAll();

	Image findById(int id);

	Image create(Image image);

	Image update(Image image);
}

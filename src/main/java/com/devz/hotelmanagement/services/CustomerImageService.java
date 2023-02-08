package com.devz.hotelmanagement.services;

import java.util.List;

import com.devz.hotelmanagement.entities.CustomerImage;

public interface CustomerImageService {
	List<CustomerImage> findAll();

	CustomerImage findById(int id);

	CustomerImage create(CustomerImage customerImage);

	CustomerImage upadte(CustomerImage customerImage);
}

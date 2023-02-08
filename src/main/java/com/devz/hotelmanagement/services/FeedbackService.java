package com.devz.hotelmanagement.services;

import java.util.List;

import com.devz.hotelmanagement.entities.Feedback;

public interface FeedbackService {
	List<Feedback> findAll();

	Feedback findById(int id);

	Feedback create(Feedback feedback);

	Feedback update(Feedback feedback);
}

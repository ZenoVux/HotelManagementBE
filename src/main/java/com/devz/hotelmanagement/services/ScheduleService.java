package com.devz.hotelmanagement.services;

import java.util.List;

import com.devz.hotelmanagement.entities.Schedule;

public interface ScheduleService {
	List<Schedule> findAll();

	Schedule findById(int id);

	Schedule create(Schedule schedule);

	Schedule update(Schedule schedule);
}

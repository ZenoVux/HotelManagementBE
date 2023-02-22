package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.Schedule;
import com.devz.hotelmanagement.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devz.hotelmanagement.services.ScheduleService;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Override
    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    @Override
    public Schedule findById(int id) {
        Optional<Schedule> optional = scheduleRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public Schedule findByCode(String code) {
        return null;
    }

    @Override
    public Schedule create(Schedule schedule) {
        schedule.setId(null);
        return scheduleRepository.save(schedule);
    }

    @Override
    public Schedule update(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }
}

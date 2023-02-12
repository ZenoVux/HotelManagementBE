package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.Schedule;
import com.devz.hotelmanagement.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/schedules")
public class ScheduleRestController {
    @Autowired
    ScheduleService scheduleService;

    @GetMapping
    public List<Schedule> getAll() {
        return scheduleService.findAll();
    }

    @PostMapping
    public Schedule create(@RequestBody Schedule schedule) {
        return scheduleService.create(schedule);
    }

    @PutMapping
    public Schedule update(@RequestBody Schedule schedule) {
        return scheduleService.update(schedule);
    }
}

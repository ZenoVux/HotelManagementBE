package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.Schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

}

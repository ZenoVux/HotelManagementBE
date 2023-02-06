package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.Feedback;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Integer, Feedback> {

}

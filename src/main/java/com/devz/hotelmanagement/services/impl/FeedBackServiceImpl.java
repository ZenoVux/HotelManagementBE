package com.devz.hotelmanagement.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devz.hotelmanagement.entities.Feedback;
import com.devz.hotelmanagement.repositories.FeedbackRepository;
import com.devz.hotelmanagement.services.FeedbackService;

@Service
public class FeedBackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepo;

    @Override
    public List<Feedback> findAll() {
        return feedbackRepo.findAll();
    }

    @Override
    public Feedback findById(int id) {
        Optional<Feedback> optional = feedbackRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public Feedback create(Feedback feedback) {
        feedback.setId(null);
        return feedbackRepo.save(feedback);
    }

    @Override
    public Feedback update(Feedback feedback) {
        return feedbackRepo.save(feedback);
    }

}

package com.devz.hotelmanagement.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devz.hotelmanagement.entities.Feedback;
import com.devz.hotelmanagement.services.FeedbackService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackRestController {
	@Autowired
    private FeedbackService feedbackService;

    @GetMapping
    public List<Feedback> getAll() {
        return feedbackService.findAll();
    }

    @PostMapping
    public Feedback create(@RequestBody Feedback feedback) {
        return feedbackService.create(feedback);
    }

    @PutMapping
    public Feedback update(@RequestBody Feedback feedback) {
        return feedbackService.update(feedback);
    }
}

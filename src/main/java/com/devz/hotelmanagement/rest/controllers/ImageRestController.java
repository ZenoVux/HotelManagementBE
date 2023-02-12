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

import com.devz.hotelmanagement.entities.Image;
import com.devz.hotelmanagement.services.ImageService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/images")
public class ImageRestController {
	@Autowired
    private ImageService imageService;

    @GetMapping
    public List<Image> getAll() {
        return imageService.findAll();
    }

    @PostMapping
    public Image create(@RequestBody Image image) {
        return imageService.create(image);
    }

    @PutMapping
    public Image update(@RequestBody Image image) {
        return imageService.update(image);
    }
}

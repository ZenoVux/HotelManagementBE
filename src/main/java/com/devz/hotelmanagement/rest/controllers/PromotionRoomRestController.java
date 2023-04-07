package com.devz.hotelmanagement.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devz.hotelmanagement.entities.PromotionRoom;
import com.devz.hotelmanagement.services.PromotionRoomService;
import com.devz.hotelmanagement.services.PromotionService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/promotion-rooms")
public class PromotionRoomRestController {
	@Autowired
	PromotionRoomService promotionRoomService;
	
		@GetMapping
	    public List<PromotionRoom> getAll() {
	        return promotionRoomService.findAll();
	    }

	    @PostMapping
	    public PromotionRoom create(@RequestBody PromotionRoom promotionRoom) {
	        return promotionRoomService.create(promotionRoom);
	    }

	    @PutMapping
	    public PromotionRoom update(@RequestBody PromotionRoom promotionRoom) {
	        return promotionRoomService.update(promotionRoom);
	    }

	    @DeleteMapping("/{id}")
	    public void delete(@PathVariable("id") Integer id) {
	    	promotionRoomService.delete(id);
	    }
}

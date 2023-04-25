package com.devz.hotelmanagement.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devz.hotelmanagement.entities.RoomTypePromotion;
import com.devz.hotelmanagement.services.PromotionRoomService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/promotion-rooms")
public class PromotionRoomRestController {
	@Autowired
	PromotionRoomService promotionRoomService;
	
	@GetMapping
	public List<RoomTypePromotion> getAll() {
		return promotionRoomService.findAll();
	}

	@GetMapping("/curr-by-room-type/{code}")
	public ResponseEntity<?> findCurrByRoomTypeCode(@PathVariable("code") String code) {
		RoomTypePromotion roomTypePromotion = promotionRoomService.findCurrByRoomTypeCode(code);
		return ResponseEntity.status(HttpStatus.OK).body(roomTypePromotion);
	}

	@PostMapping
	public RoomTypePromotion create(@RequestBody RoomTypePromotion roomTypePromotion) {
		return promotionRoomService.create(roomTypePromotion);
	}

	@PutMapping
	public RoomTypePromotion update(@RequestBody RoomTypePromotion roomTypePromotion) {
		return promotionRoomService.update(roomTypePromotion);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Integer id) {
	    	promotionRoomService.delete(id);
	    }
}

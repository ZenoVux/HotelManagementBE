package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.Promotion;
import com.devz.hotelmanagement.entities.Room;
import com.devz.hotelmanagement.entities.RoomImage;
import com.devz.hotelmanagement.entities.RoomType;
import com.devz.hotelmanagement.entities.ServiceRoom;
import com.devz.hotelmanagement.services.PromotionService;
import com.devz.hotelmanagement.services.RoomImageService;
import com.devz.hotelmanagement.services.RoomService;
import com.devz.hotelmanagement.services.RoomTypeService;
import com.devz.hotelmanagement.services.ServiceRoomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/booking-online")
public class BookingOnlineRestController {

	@Autowired
	private ServiceRoomService serviceRoomService;

	@Autowired
	private RoomImageService roomImageService;

	@Autowired
	private RoomTypeService roomTypeService;

	@Autowired
	private RoomService roomService;
	
	@Autowired
    private PromotionService promotionService;

	@GetMapping("/room-types")
	public List<RoomType> findAllRoomType() {
		return roomTypeService.findAll();
	}

	@GetMapping("/services")
	public List<ServiceRoom> findAllService() {
		return serviceRoomService.findAll();
	}
	
	@GetMapping("/promotions")
	public List<Promotion> findAllPomotion() {
		return promotionService.findAll();
	}

	@GetMapping("/rooms")
	public List<Room> findAllRooms() {
		return roomService.findAll();
	}

	@GetMapping("/room-types/{code}")
	public RoomType findRoomTypeByCode(@PathVariable("code") String code) {
		return roomTypeService.findByCode(code);
	}

	@GetMapping("/img/{codeRoom}")
	public List<RoomImage> getByCodeRoom(@PathVariable("codeRoom") String codeRoom) {
		return roomImageService.getListByCodeRoom(codeRoom);
	}

}

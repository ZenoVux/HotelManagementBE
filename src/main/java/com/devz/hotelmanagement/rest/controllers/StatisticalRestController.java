package com.devz.hotelmanagement.rest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devz.hotelmanagement.repositories.StatisticalRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/statistical")
public class StatisticalRestController {
	
	@Autowired
	StatisticalRepository statisticalRepository;
	
	@GetMapping("/rooms/top")
	public List<Object[]> getTopBookedRooms() {
		return statisticalRepository.findRoomsByNumBookings();
	}
	
	@GetMapping("/type/top")
	public List<Object[]> getTopBookedRoomTypes() {
		return statisticalRepository.findRoomTypesByNumBookings();
	}
	
	@GetMapping("/ser/top")
	public List<Object[]> getTopUsedSer() {
		return statisticalRepository.findTop4ServicesUsed();
	}
	
	@GetMapping("/totals")
	public ResponseEntity<Map<String, Integer>> getTotals() {
		List<Object[]> Result = statisticalRepository.getTotal();
		Map<String, Integer> totals = new HashMap<>();

		for (Object[] result : Result) {
			String key = result[0].toString();
			Integer value = result[1] != null ? Integer.parseInt(result[1].toString()) : 0;
			totals.put(key, value);
		}

		return new ResponseEntity<>(totals, HttpStatus.OK);
	}
	
	@GetMapping("/byDate/{startDate}/{endDate}")
	public ResponseEntity<Integer> getTotalsByDateRange(@PathVariable("startDate") String startDate,
			@PathVariable("endDate") String endDate) {
		Integer totalAmount = statisticalRepository.getTotalByDateRange(startDate, endDate);
		if (totalAmount == null) {
			totalAmount = 0;
		}
		return new ResponseEntity<>(totalAmount, HttpStatus.OK);
	}
	
	@GetMapping("/google-chart")
    public List<Object[]> getRoomTypeReport() {
        return statisticalRepository.getTypeRoomGoogleChart();
    }
	
}

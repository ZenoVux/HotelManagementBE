package com.devz.hotelmanagement.configs;

import lombok.Data;

@Data
public class EmailRequest {
	private String email;
	private String bookingCode;
}

package com.devz.hotelmanagement.configs;

import lombok.Data;

@Data
public class PasswordRequest {
	private String newPassword;
	private String token;
}

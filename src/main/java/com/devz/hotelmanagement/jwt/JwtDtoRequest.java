package com.devz.hotelmanagement.jwt;

import lombok.Data;

@Data
public class JwtDtoRequest {
	private String username;
	private String password;
}

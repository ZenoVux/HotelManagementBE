package com.devz.hotelmanagement.jwt;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devz.hotelmanagement.entities.Account;
import com.devz.hotelmanagement.services.AccountService;

import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/login")
public class JwtRestController {
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	private AccountService accountService;

//	  @PostMapping("/add")
//	    public Account addAcc(@RequestBody JwtDtoRequest authRequest){
//	    	Account account2 = new Account();
//	    	account2.setUsername(authRequest.getUsername());
//	    	account2.setPassword(passwordEncoder.encode(authRequest.getPassword()));
//	    	account2.setAddress("hanoi");
//	    	account2.setEmail("hung@gmail.com");
//	    	account2.setFullName("NamVietHotel");
//	    	account2.setPhoneNumber("01324535233");
//	    	account2.setStatus(true);
//		  return accountService.create(account2);
//	    }

	@PostMapping
	public ResponseEntity<JwtDtoResponse>  getTokenForAuthenticatedUser(@RequestBody JwtDtoRequest authRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
			if (authentication.isAuthenticated()) {
				JwtDtoResponse jwtDtoResp = new JwtDtoResponse();
				List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
				boolean isAdmin = roles.contains("ADMIN");
				System.out.println(roles);
				jwtDtoResp.setToken(jwtService.generateToken(authRequest.getUsername(),isAdmin));
				return ResponseEntity.ok(jwtDtoResp);
			}
		} catch (AuthenticationException e) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }
	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	


}
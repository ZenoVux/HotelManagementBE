package com.devz.hotelmanagement.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import com.devz.hotelmanagement.entities.Account;
import com.devz.hotelmanagement.services.impl.AccountServiceImpl;

@CrossOrigin("*")
@RestController
@RequestMapping("/reset-password")
public class EmailController {

    @Autowired
    private AccountServiceImpl acc ;
    
    @Autowired
    MyEmailService emailService;

    @PostMapping
    public ResponseEntity<EmailRequest> sendEmail(@RequestBody EmailRequest emailRequest) {
        String token = UUID.randomUUID().toString();
        try {
			acc.updateRePasswordToken(token, emailRequest.getEmail());
			String resetPasswordLink = "http://127.0.0.1:5500/#!/reset-password/" + token;
			emailService.sendSimpleMessage(emailRequest.getEmail(), "ResetPassword NamViet Hotel", "Click here : "+resetPasswordLink);
			return ResponseEntity.ok(emailRequest);
		} catch (Exception e) {
			 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
    }
    
    @PostMapping("/done")
    public ResponseEntity<PasswordRequest> resetPass(@RequestBody PasswordRequest pss) {
    	Account account = acc.getAccByRepasswordToken(pss.getToken());
       if (account == null) {
    	   return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}else {
		acc.updatePassword(account, pss.getNewPassword());
		return ResponseEntity.ok(pss);
	}
    }
    
    
}


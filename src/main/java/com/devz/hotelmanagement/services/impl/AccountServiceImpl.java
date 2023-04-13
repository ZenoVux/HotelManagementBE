package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.Account;
import com.devz.hotelmanagement.repositories.AccountRepository;
import com.devz.hotelmanagement.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import javax.security.auth.login.AccountNotFoundException;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired 
	PasswordEncoder passwordEncoder;
	
    @Autowired
    private AccountRepository accountRepo;

    @Override
    public List<Account> findAll() {
        return accountRepo.findAll();
    }

    @Override
    public Account findById(int id) {
        Optional<Account> optional = accountRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public Account findByCode(String code) {
        return null;
    }

    @Override
    public Account create(Account account) {
        account.setId(null);
        return accountRepo.save(account);
    }

    @Override
    public Account update(Account account) {
        return accountRepo.save(account);
    }
    
    public void updateRePasswordToken(String token, String email) throws AccountNotFoundException{
    	Account acc = accountRepo.findByEmail(email);
    	 if (acc != null) {
    		 acc.setRePasswordToken(token);
             accountRepo.save(acc);
         } else {
             throw new AccountNotFoundException("Could not find any  with the email " + email);
         }
    }
    
    public Account getAccByRepasswordToken(String token) {
    	return accountRepo.findByRePasswordToken(token);
    }
    
    public void updatePassword(Account acc, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        acc.setPassword(encodedPassword);
         
        acc.setRePasswordToken(null);
        accountRepo.save(acc);
    }

    @Override
    public Account findByUsernameAndActivate(String username) {
        Optional<Account> optional = accountRepo.findByUsernameAndActivate(username);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

}

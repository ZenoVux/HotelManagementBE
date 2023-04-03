package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.Account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
Optional<Account> findByUsername(String username);
	
	Account findByEmail(String email);
	
	Account findByRePasswordToken(String rePasswordToken);
}

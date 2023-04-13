package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.Account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

	@Query("SELECT acc FROM Account acc WHERE acc.username = :username AND acc.status = true")
	Optional<Account> findByUsernameAndActivate(@Param("username") String username);
	
	Account findByEmail(String email);
	
	Account findByRePasswordToken(String rePasswordToken);
}

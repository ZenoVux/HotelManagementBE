package com.devz.hotelmanagement.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.devz.hotelmanagement.entities.Account;
import com.devz.hotelmanagement.entities.UserRole;
import com.devz.hotelmanagement.repositories.AccountRepository;
import com.devz.hotelmanagement.repositories.UserRoleRepository;

@Component
public class UserDetailService implements UserDetailsService {

	@Autowired
	AccountRepository accRe;
	@Autowired
	UserRoleRepository roleRe;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accRe.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("No user found"));

		List<UserRole> userRoles = roleRe.findByAccountId(account.getId());
		List<GrantedAuthority> authorities = userRoles.stream()
				.map(userRole -> new SimpleGrantedAuthority(userRole.getRole().getCode())).collect(Collectors.toList());
//		System.out.print(authorities);
		return new User(account.getUsername(), account.getPassword(), authorities);

	}

}

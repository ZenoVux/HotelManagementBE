package com.devz.hotelmanagement.services;

import java.util.List;

import com.devz.hotelmanagement.entities.Role;

public interface RoleService {
	List<Role> findAll();

	Role findById(int id);

	Role create(Role role);

	Role update(Role role);

}

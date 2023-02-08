package com.devz.hotelmanagement.services;

import java.util.List;

import com.devz.hotelmanagement.entities.UserRole;

public interface UserRoleService {
	List<UserRole> findAll();

	UserRole findById(int id);

	UserRole create(UserRole userRole);

	UserRole update(UserRole userRole);
}

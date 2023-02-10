package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.Role;
import com.devz.hotelmanagement.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devz.hotelmanagement.services.RoleService;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepo;

    @Override
    public List<Role> findAll() {
        return roleRepo.findAll();
    }

    @Override
    public Role findById(int id) {
        Optional<Role> optional = roleRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public Role create(Role role) {
        role.setId(null);
        return roleRepo.save(role);
    }

    @Override
    public Role update(Role role) {
        return roleRepo.save(role);
    }
}

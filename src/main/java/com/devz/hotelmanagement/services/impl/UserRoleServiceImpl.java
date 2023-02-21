package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.UserRole;
import com.devz.hotelmanagement.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devz.hotelmanagement.services.UserRoleService;

import java.util.List;
import java.util.Optional;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    UserRoleRepository userRoleRepository;

    @Override
    public List<UserRole> findAll() {
        return userRoleRepository.findAll();
    }

    @Override
    public UserRole findById(int id) {
        Optional<UserRole> optional = userRoleRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public UserRole findByCode(String code) {
        return null;
    }

    @Override
    public UserRole create(UserRole userRole) {
        userRole.setId(null);
        return userRoleRepository.save(userRole);
    }

    @Override
    public UserRole update(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    @Override
    public List<UserRole> findByAccountId(Integer accountId) {
        return userRoleRepository.findByAccountId(accountId);
    }

    @Override
    public void delete(Integer id) {
        userRoleRepository.deleteById(id);
    }
}

package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.UserRole;
import com.devz.hotelmanagement.services.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/user-roles")
public class UserRoleRestController {

    @Autowired
    private UserRoleService userRoleService;

    @GetMapping
    public List<UserRole> getAll() {
        return userRoleService.findAll();
    }

    @PostMapping
    public UserRole create(@RequestBody UserRole userRole) {
        return userRoleService.create(userRole);
    }

    @PutMapping
    public UserRole update(@RequestBody UserRole userRole) {
        return userRoleService.update(userRole);
    }

}

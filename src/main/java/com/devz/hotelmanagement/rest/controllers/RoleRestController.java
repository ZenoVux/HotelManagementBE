package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.Role;
import com.devz.hotelmanagement.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/roles")
public class RoleRestController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public List<Role> getAll() {
        return roleService.findAll();
    }

    @PostMapping
    public Role create(@RequestBody Role role) {
        return roleService.create(role);
    }

    @PutMapping
    public Role update(@RequestBody Role role) {
        return roleService.update(role);
    }
}

package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.Account;
import com.devz.hotelmanagement.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/accounts")
public class AccountRestController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<Account> getAll() {
        return accountService.findAll();
    }

    @PostMapping
    public Account create(@RequestBody Account account) {
        return accountService.create(account);
    }

    @PutMapping
    public Account update(@RequestBody Account account) {
        return accountService.update(account);
    }
}

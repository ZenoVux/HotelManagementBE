package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.Account;
import com.devz.hotelmanagement.models.AccountReq;
import com.devz.hotelmanagement.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/accounts")
public class AccountRestController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<Account> getAll() {
        return accountService.findAll();
    }

    @GetMapping("/{username}")
    public ResponseEntity<Account> findByUsername(@PathVariable("username") Optional<String> username) {
        if (!username.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Account account = accountService.findByUsername(username.get());
        if (account != null) {
            return ResponseEntity.ok(account);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity<Account> create(@RequestBody AccountReq accountReq) {
        Account account = new Account();
        account.setFullName(accountReq.getFullName());
        if (accountService.findByUsername(accountReq.getUsername()) != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        account.setUsername(accountReq.getUsername());
        if (!accountReq.getPassword().equals(accountReq.getConfirmPassword())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        account.setPassword(passwordEncoder.encode(accountReq.getPassword()));
        account.setPhoneNumber(accountReq.getPhoneNumber());
        account.setEmail(accountReq.getEmail());
        account.setStatus(accountReq.getStatus());
        if (accountService.create(account) != null) {
            return ResponseEntity.ok(account);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<Account> update(@RequestBody AccountReq accountReq) {
        Account account = accountService.findByUsername(accountReq.getUsername());
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        account.setFullName(accountReq.getFullName());
        if (!accountReq.getPassword().equals(accountReq.getConfirmPassword())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        account.setPassword(passwordEncoder.encode(accountReq.getPassword()));
        account.setPhoneNumber(accountReq.getPhoneNumber());
        account.setEmail(accountReq.getEmail());
        if (accountService.update(account) != null) {
            return ResponseEntity.ok(account);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/activate/{username}")
    public ResponseEntity<Void> activate(@PathVariable("username") Optional<String> username) {
        if (!username.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Account account = accountService.findByUsername(username.get());
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        account.setStatus(true);
        if (accountService.update(account) != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/deactivate/{username}")
    public ResponseEntity<Void> deactivate(@PathVariable("username") Optional<String> username) {
        if (!username.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Account account = accountService.findByUsername(username.get());
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        account.setStatus(false);
        if (accountService.update(account) != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}

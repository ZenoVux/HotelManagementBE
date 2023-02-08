package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.Account;
import com.devz.hotelmanagement.repositories.AccountRepository;
import com.devz.hotelmanagement.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepo;

    @Override
    public List<Account> findAll() {
        return accountRepo.findAll();
    }

    @Override
    public Account findById(int id) {
        Optional<Account> optional = accountRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public Account create(Account account) {
        account.setId(null);
        return accountRepo.save(account);
    }

    @Override
    public Account update(Account account) {
        return accountRepo.save(account);
    }
}

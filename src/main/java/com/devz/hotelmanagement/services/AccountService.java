package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.Account;

import java.util.List;

public interface AccountService {

    List<Account> findAll();

    Account findById(int id);

    Account create(Account account);

    Account upadte(Account account);
}

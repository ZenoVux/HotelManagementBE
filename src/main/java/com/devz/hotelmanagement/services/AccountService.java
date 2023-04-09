package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.Account;

import java.util.Optional;

public interface AccountService extends ServiceBase<Account> {

    Account findByUsername(String username);

}

package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.Customer;

public interface CustomerService extends ServiceBase<Customer> {

    Customer searchByPeopleId(String peopleId);
}

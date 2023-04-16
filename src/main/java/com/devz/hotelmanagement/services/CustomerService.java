package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.Customer;

import java.util.List;

public interface CustomerService extends ServiceBase<Customer> {

    Customer searchByPeopleId(String peopleId);

    Customer findByPhoneNumber(String phoneNumber);

    List<Customer> getCustomerInUse();

}

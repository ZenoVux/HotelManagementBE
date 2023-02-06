package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Integer, Customer> {

}

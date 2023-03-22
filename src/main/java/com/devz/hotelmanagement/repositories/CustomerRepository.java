package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("SELECT c FROM Customer c WHERE c.phoneNumber = :phoneNumber")
     Customer findByPhoneNumber(@Param("phoneNumber") String phoneNumber);

}

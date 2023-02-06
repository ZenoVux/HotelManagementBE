package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.PaymentMethod;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepository extends JpaRepository<Integer, PaymentMethod> {

}

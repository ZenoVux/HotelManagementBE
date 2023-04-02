package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.PaymentMethod;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {

    @Query("SELECT pm FROM PaymentMethod pm WHERE pm.code = :code")
    Optional<PaymentMethod> findByCode(@Param("code") String code);

    @Query("SELECT pm FROM PaymentMethod pm WHERE pm.status = 1")
    List<PaymentMethod> findPaymentInUse();

}

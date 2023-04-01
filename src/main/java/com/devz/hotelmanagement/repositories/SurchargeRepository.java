package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.Surcharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SurchargeRepository extends JpaRepository<Surcharge, Integer> {

    @Query(value = "SELECT * FROM surcharges WHERE surcharges.type = false", nativeQuery = true)
    List<Surcharge> findEarlyCheckinSurcharges();

    @Query(value = "SELECT * FROM surcharges WHERE surcharges.type = true", nativeQuery = true)
    List<Surcharge> findLateCheckoutSurcharges();

}

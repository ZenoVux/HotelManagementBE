package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.Surcharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SurchargeRepository extends JpaRepository<Surcharge, Integer> {

    @Query(value = "SELECT * FROM surcharges WHERE surcharges.type = false", nativeQuery = true)
    List<Surcharge> findEarlyCheckinSurcharges();

    @Query(value = "SELECT * FROM surcharges WHERE surcharges.type = true", nativeQuery = true)
    List<Surcharge> findLateCheckoutSurcharges();

    @Query("SELECT s FROM Surcharge s WHERE s.code = :code")
    Optional<Surcharge> findByCode(@Param("code") String code);

    @Query(value = "SELECT surcharges.code FROM surcharges ORDER BY surcharges.code DESC LIMIT 1", nativeQuery = true)
    String getMaxCode();
}

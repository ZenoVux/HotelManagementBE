package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.UsedService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsedServiceRepository extends JpaRepository<UsedService, Integer> {

}

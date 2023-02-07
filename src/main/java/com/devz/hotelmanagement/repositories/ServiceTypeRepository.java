package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.ServiceType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Integer> {

}

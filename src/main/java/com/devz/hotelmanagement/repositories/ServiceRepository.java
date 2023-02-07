package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.Service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {

}

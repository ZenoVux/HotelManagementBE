package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.CustomerType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerTypeRepository extends JpaRepository<Integer, CustomerType> {

}

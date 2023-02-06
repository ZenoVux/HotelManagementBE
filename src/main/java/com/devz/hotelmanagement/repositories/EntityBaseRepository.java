package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.EntityBase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityBaseRepository extends JpaRepository<Integer, EntityBase> {

}

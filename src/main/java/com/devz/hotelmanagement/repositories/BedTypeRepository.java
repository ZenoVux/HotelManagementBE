package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.BedType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BedTypeRepository extends JpaRepository<Integer, BedType> {

}

package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.CustomerImage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerImageRepository extends JpaRepository<CustomerImage, Integer> {

}

package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.Image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Integer, Image> {

}

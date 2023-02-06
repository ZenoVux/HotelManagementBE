package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.RoomType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomTypeRepository extends JpaRepository<Integer, RoomType> {

}

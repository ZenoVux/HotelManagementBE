package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.RoomDetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomDetailRepository extends JpaRepository<RoomDetail, Integer> {

}

package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.Room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query("SELECT p FROM Room p WHERE p.code=?1")
    Room findByCode(String code);
}

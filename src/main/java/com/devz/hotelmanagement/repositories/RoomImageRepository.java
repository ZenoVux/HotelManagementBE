package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.RoomImage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomImageRepository extends JpaRepository<RoomImage, Integer> {
    @Query("SELECT p FROM RoomImage p WHERE p.room.code=?1")
    List<RoomImage> getListByCodeRoom(String code);
}

package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.RoomTypeImage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomTypeImageRepository extends JpaRepository<RoomTypeImage, Integer> {

    @Query("SELECT p FROM RoomTypeImage p WHERE p.roomType.code=?1")
    List<RoomTypeImage> getListByCodeRoom(String code);

}

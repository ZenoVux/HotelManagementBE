package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.Room;

import com.devz.hotelmanagement.models.RoomStatusCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    @Query("SELECT p FROM Room p WHERE p.code=?1")
    Room findByCode(String code);

    @Query("SELECT new com.devz.hotelmanagement.models.RoomStatusCount(r.status, COUNT(r)) FROM Room r GROUP BY r.status")
    List<RoomStatusCount> getStatusCount();

    @Query("SELECT room FROM Room room WHERE room.roomType.code = :roomType")
    List<Room> getRoomBookings(@Param("roomType") String roomType);

}

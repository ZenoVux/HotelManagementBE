package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.Room;

import com.devz.hotelmanagement.models.RoomStatusCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    @Query("SELECT new com.devz.hotelmanagement.models.RoomStatusCount(r.status, COUNT(r)) FROM Room r GROUP BY r.status")
    List<RoomStatusCount> getStatusCount();
}

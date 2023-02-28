package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.BedRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BedRoomRepository extends JpaRepository<BedRoom, Integer> {

    @Query("SELECT bedRoom FROM BedRoom bedRoom WHERE bedRoom.room.id = :id")
    List<BedRoom> getBedRoomsByRoomId(@Param("id") Integer id);

}

package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.Room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    @Query(value = "CALL GET_ROOM_STATUS_COUNT", nativeQuery = true)
    List<Object[]> getStatusCount();

    @Query(value = "CALL GET_HOTEL_ROOMS", nativeQuery = true)
    List<Object[]> getHotelRoom();

    @Query("SELECT p FROM Room p WHERE p.code=?1")
    Room findByCode(String code);

    @Modifying
    @Query("UPDATE Room r SET r.status = :status WHERE r.code = :code")
    void updateRoomStatusByCode(@Param("code") String code, @Param("status") Integer status);

    @Query("SELECT room FROM Room room WHERE room.floor.id = :id")
    List<Room> getByFloorId(int id);

}

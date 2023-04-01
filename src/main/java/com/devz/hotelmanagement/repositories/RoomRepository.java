package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.Room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
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

    @Query("SELECT room FROM Room room ORDER BY room.code ASC")
    List<Room> findAllByCodeASC();

    // tìm các phòng không đặt trước
    @Query("SELECT r " +
            "FROM Room r " +
            "WHERE r.code NOT IN (SELECT bkd.room.code " +
            "                       FROM BookingDetail bkd " +
            "                       WHERE bkd.booking.checkinExpected < :checkout AND " +
            "                           bkd.booking.checkoutExpected > :checkin AND " +
            "                           bkd.status = 1 AND " + // trạng thái chờ nhận
            "                           bkd.booking.status = 1 AND " + // trạng thái chờ xác nhận
            "                           bkd.booking.status = 2)" + // trạng thái đã xác nhận
            "   AND r.code NOT IN (SELECT ivd.room.code " +
            "                       FROM InvoiceDetail ivd " +
            "                       WHERE ivd.checkinExpected < :checkout AND " +
            "                           ivd.checkoutExpected > :checkin AND " +
            "                           ivd.status = 1)" + // trạng thái đang sử dụng
            "ORDER BY r.code ASC")
    List<Room> findUnbookedRoomsByCheckinAndCheckout(@Param("checkin") Date checkin, @Param("checkout") Date checkout);

}

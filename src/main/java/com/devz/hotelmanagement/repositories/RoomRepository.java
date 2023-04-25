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

    @Query("SELECT p FROM Room p WHERE p.code=?1")
    Room findByCode(String code);

    @Query("SELECT room FROM Room room WHERE room.floor.id = :id")
    List<Room> getByFloorId(int id);

    @Query("SELECT room FROM Room room ORDER BY room.code ASC")
    List<Room> findAllByCodeASC();

    @Query(value = "SELECT code FROM rooms WHERE floor_id = :id ORDER BY code DESC LIMIT 1", nativeQuery = true)
    String getMaxCode(int id);

    // tìm các phòng không đặt trước
    @Query("SELECT r " +
            "FROM Room r " +
            "WHERE r.code NOT IN (SELECT bkd.room.code " +
            "                       FROM BookingDetail bkd " +
            "                       WHERE ((bkd.checkinExpected >= :checkinDate AND bkd.checkinExpected < :checkoutDate) OR " +
            "                               (bkd.checkoutExpected > :checkinDate AND bkd.checkoutExpected <= :checkoutDate) OR " +
            "                               (bkd.checkinExpected <= :checkinDate AND bkd.checkoutExpected >= :checkoutDate)) AND " +
            "                           bkd.status = 1 AND " + // trạng thái chờ nhận
            "                           (bkd.booking.status = 1 OR " + // trạng thái chờ xác nhận
            "                           bkd.booking.status = 2 OR " + // trạng thái đã xác nhận
            "                           bkd.booking.status = 3))" + // trạng thái đã xử lý
            "   AND r.code NOT IN (SELECT ivd.room.code " +
            "                       FROM InvoiceDetail ivd " +
            "                       WHERE ((ivd.checkinExpected >= :checkinDate AND ivd.checkinExpected < :checkoutDate) OR " +
            "                               (ivd.checkoutExpected > :checkinDate AND ivd.checkoutExpected <= :checkoutDate) OR " +
            "                               (ivd.checkinExpected <= :checkinDate AND ivd.checkoutExpected >= :checkoutDate)) AND " +
            "                           ivd.status = 1)" + // trạng thái đang sử dụng
            "ORDER BY r.code ASC")
    List<Room> findUnbookedRoomsByCheckinAndCheckout(@Param("checkinDate") Date checkinDate, @Param("checkoutDate") Date checkoutDate);

    @Query(value =
            "SELECT " +
            "CONCAT(rt.name, ' | ', GROUP_CONCAT(DISTINCT CONCAT(br.quantity_bed, ' ', bt.name) ORDER BY bt.name SEPARATOR ' & ')) AS category_name " +
            "FROM " +
            "rooms r " +
            "LEFT JOIN room_types rt ON r.room_type_id = rt.id " +
            "LEFT JOIN bed_rooms br ON r.id = br.room_id " +
            "LEFT JOIN bed_types bt ON br.bed_type_id = bt.id " +
            "WHERE " +
            "r.code = :roomCode " +
            "GROUP BY " +
            "rt.id ", nativeQuery = true)
    String getNameTypeByRoomCode(@Param("roomCode") String roomCode);

}

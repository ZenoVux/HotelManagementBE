package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.RoomType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {

    @Query("SELECT rt FROM RoomType rt WHERE rt.code= :code")
    RoomType findByCode(@Param("code") String code);

}

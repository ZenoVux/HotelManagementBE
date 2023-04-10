package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.ServiceRoom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRoomRepository extends JpaRepository<ServiceRoom, Integer> {

    @Query("SELECT s FROM ServiceRoom s WHERE s.status = :status")
    List<ServiceRoom> findByStatus(@Param("status") Boolean status);

}

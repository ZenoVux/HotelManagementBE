package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.ServiceRoom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRoomRepository extends JpaRepository<ServiceRoom, Integer> {

}

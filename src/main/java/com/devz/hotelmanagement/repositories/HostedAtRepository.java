package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.HostedAt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostedAtRepository extends JpaRepository<Integer, HostedAt> {

}

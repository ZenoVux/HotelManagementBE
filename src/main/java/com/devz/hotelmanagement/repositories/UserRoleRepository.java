package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.UserRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    @Query("SELECT ur FROM UserRole ur WHERE ur.account.id = :accountId")
    List<UserRole> findByAccountId(@Param("accountId") Integer accountId);
}

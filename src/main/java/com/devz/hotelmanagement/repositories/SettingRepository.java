package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Integer> {

    @Query("SELECT s FROM Setting s WHERE s.code='SYSTEM'")
    Setting getSetting();

}

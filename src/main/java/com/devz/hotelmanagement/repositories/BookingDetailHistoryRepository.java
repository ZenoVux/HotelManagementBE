package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.BookingDetailHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingDetailHistoryRepository extends JpaRepository<BookingDetailHistory, Integer> {

}

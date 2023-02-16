package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.InvoiceDetailHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceDetailHistoryRepository extends JpaRepository<InvoiceDetailHistory, Integer> {

}

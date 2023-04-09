package com.devz.hotelmanagement.repositories;

import com.devz.hotelmanagement.entities.InvoiceDetailHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceDetailHistoryRepository extends JpaRepository<InvoiceDetailHistory, Integer> {

    @Query("SELECT i FROM InvoiceDetailHistory i WHERE i.invoiceDetail.id = :invoiceDetailId")
    List<InvoiceDetailHistory> findAllByInvoiceDetailId(Integer invoiceDetailId);
}

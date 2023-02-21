package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.InvoiceDetailHistory;
import com.devz.hotelmanagement.repositories.InvoiceDetailHistoryRepository;
import com.devz.hotelmanagement.services.InvoiceDetailHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceDetailHistoryServiceImpl implements InvoiceDetailHistoryService {

    @Autowired
    private InvoiceDetailHistoryRepository invoiceDetailHistoryRepo;

    @Override
    public List<InvoiceDetailHistory> findAll() {
        return invoiceDetailHistoryRepo.findAll();
    }

    @Override
    public InvoiceDetailHistory findById(int id) {
        Optional<InvoiceDetailHistory> optional = invoiceDetailHistoryRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public InvoiceDetailHistory findByCode(String code) {
        return null;
    }

    @Override
    public InvoiceDetailHistory create(InvoiceDetailHistory invoiceDetailHistory) {
        invoiceDetailHistory.setId(null);
        return invoiceDetailHistoryRepo.save(invoiceDetailHistory);
    }

    @Override
    public InvoiceDetailHistory update(InvoiceDetailHistory invoiceDetailHistory) {
        return invoiceDetailHistoryRepo.save(invoiceDetailHistory);
    }

}

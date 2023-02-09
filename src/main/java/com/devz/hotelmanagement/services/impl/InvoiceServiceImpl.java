package com.devz.hotelmanagement.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devz.hotelmanagement.entities.Invoice;
import com.devz.hotelmanagement.repositories.InvoiceRepository;
import com.devz.hotelmanagement.services.InvoiceService;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    InvoiceRepository invoiceRepo;

    @Override
    public List<Invoice> findAll() {
        return invoiceRepo.findAll();
    }

    @Override
    public Invoice findById(int id) {
        Optional<Invoice> optional = invoiceRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public Invoice create(Invoice invoice) {
        invoice.setId(null);
        return invoiceRepo.save(invoice);
    }

    @Override
    public Invoice update(Invoice invoice) {
        return invoiceRepo.save(invoice);
    }

}

package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.InvoiceType;
import com.devz.hotelmanagement.repositories.InvoiceRepository;
import com.devz.hotelmanagement.repositories.InvoiceTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devz.hotelmanagement.services.InvoiceTypeService;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceTypeServiceImpl implements InvoiceTypeService {
    @Autowired
    InvoiceTypeRepository invoiceTypeRepo;

    @Override
    public List<InvoiceType> findAll() {
        return invoiceTypeRepo.findAll();
    }

    @Override
    public InvoiceType findById(int id) {
        Optional<InvoiceType> optional = invoiceTypeRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public InvoiceType create(InvoiceType invoiceType) {
        invoiceType.setId(null);
        return invoiceTypeRepo.save(invoiceType);
    }

    @Override
    public InvoiceType update(InvoiceType invoiceType) {
        return invoiceTypeRepo.save(invoiceType);
    }
}

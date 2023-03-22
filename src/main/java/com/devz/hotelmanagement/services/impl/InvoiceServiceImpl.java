package com.devz.hotelmanagement.services.impl;

import java.util.List;
import java.util.Optional;

import com.devz.hotelmanagement.entities.InvoiceDetail;
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
    public Invoice findByCode(String code) {
        Optional<Invoice> optional = invoiceRepo.findByRoomCode(code);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public Invoice create(Invoice invoice) {
        invoice.setId(null);
        try {
            String maxCode = invoiceRepo.getMaxCode();
            Integer index = 1;
            if (maxCode != null) {
                index = Integer.parseInt(maxCode.replace("IV", ""));
            }
            String code = String.format("IV%05d", index + 1);
            invoice.setCode(code);
        } catch (Exception ex) {

        }
        return invoiceRepo.save(invoice);
    }

    @Override
    public Invoice update(Invoice invoice) {
        return invoiceRepo.save(invoice);
    }

    @Override
    public Invoice findCurrByRoomCode(String code) {
        Optional<Invoice> optional = invoiceRepo.findCurrByRoomCode(code);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }
}

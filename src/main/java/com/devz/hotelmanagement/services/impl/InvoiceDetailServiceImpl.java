package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.InvoiceDetail;
import com.devz.hotelmanagement.repositories.InvoiceDetailRepository;
import com.devz.hotelmanagement.services.InvoiceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceDetailServiceImpl implements InvoiceDetailService {

    @Autowired
    private InvoiceDetailRepository invoiceDetailRepo;

    @Override
    public List<InvoiceDetail> findAll() {
        return invoiceDetailRepo.findAll();
    }

    @Override
    public InvoiceDetail findById(int id) {
        Optional<InvoiceDetail> optional = invoiceDetailRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public InvoiceDetail findByCode(String code) {
        Optional<InvoiceDetail> optional = invoiceDetailRepo.findByCode(code);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public InvoiceDetail create(InvoiceDetail invoiceDetail) {
        invoiceDetail.setId(null);
        try {
            String maxCode = invoiceDetailRepo.getMaxCode();
            Integer index = 1;
            if (maxCode != null) {
                index = Integer.parseInt(maxCode.replace("IVD", ""));
                index++;
            }
            String code = String.format("IVD%05d", index);
            invoiceDetail.setCode(code);
        } catch (Exception ex) {

        }
        return invoiceDetailRepo.save(invoiceDetail);
    }

    @Override
    public InvoiceDetail update(InvoiceDetail invoiceDetail) {
        return invoiceDetailRepo.save(invoiceDetail);
    }

    @Override
    public InvoiceDetail findUsingByRoomCode(String code) {
        Optional<InvoiceDetail> optional = invoiceDetailRepo.findUsingByRoomCode(code);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public List<InvoiceDetail> findByInvoiceCode(String code) {
        return invoiceDetailRepo.findByInvoiceCode(code);
    }

    @Override
    public List<InvoiceDetail> updateAll(List<InvoiceDetail> invoiceDetails) {
        return invoiceDetailRepo.saveAll(invoiceDetails);
    }

    @Override
    public List<InvoiceDetail> findAllUsing() {
        return invoiceDetailRepo.findAllUsing();
    }

    @Override
    public List<InvoiceDetail> findByInvoiceCodeAndStatus(String code, Integer status) {
        return invoiceDetailRepo.findByInvoiceCodeAndStatus(code, status);
    }
}

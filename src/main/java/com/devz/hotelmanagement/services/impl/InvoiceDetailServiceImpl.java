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
            }
            String code = String.format("IVD%05d", index + 1);
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
    public InvoiceDetail findByCheckoutRoomCode(String code) {
        Optional<InvoiceDetail> optional = invoiceDetailRepo.findByCheckoutRoomCode(code);
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
    public void updateAll(List<InvoiceDetail> invoiceDetails) {
        invoiceDetailRepo.saveAll(invoiceDetails);
    }
}

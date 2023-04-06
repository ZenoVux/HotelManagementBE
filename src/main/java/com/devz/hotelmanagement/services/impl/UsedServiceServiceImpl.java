package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.ServiceRoom;
import com.devz.hotelmanagement.entities.UsedService;
import com.devz.hotelmanagement.repositories.UsedServiceRepository;
import com.devz.hotelmanagement.services.ServiceRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devz.hotelmanagement.services.UsedServiceService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsedServiceServiceImpl implements UsedServiceService {

    @Autowired
    UsedServiceRepository usedServiceRepo;

    @Autowired
    private ServiceRoomService serviceRoomService;

    @Override
    public List<UsedService> findAll() {
        return usedServiceRepo.findAll();
    }

    @Override
    public UsedService findById(int id) {
        Optional<UsedService> optional = usedServiceRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public UsedService findByCode(String code) {
        Optional<UsedService> optional = usedServiceRepo.findByCode(code);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public UsedService create(UsedService usedService) {
        usedService.setId(null);
        usedService.setStartedTime(new Date());
        usedService.setIsUsed(false);
        ServiceRoom serviceRoom = serviceRoomService.findById(usedService.getServiceRoom().getId());
        if (serviceRoom == null) {
            return null;
        }
        usedService.setServicePrice(serviceRoom.getPrice());
        try {
            String maxCode = usedServiceRepo.getMaxCode();
            Integer index = 1;
            if (maxCode != null) {
                index = Integer.parseInt(maxCode.replace("US", ""));
                index++;
            }
            String code = String.format("US%05d", index);
            usedService.setCode(code);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return usedServiceRepo.save(usedService);
    }

    @Override
    public UsedService update(UsedService usedService) {
        return usedServiceRepo.save(usedService);
    }

    @Override
    public List<UsedService> findByInvoiceDetailId(Integer id) {
        return usedServiceRepo.findAllByInvoiceDetailId(id);
    }

    @Override
    public List<UsedService> findAllByInvoiceDetailIdAndIsUsed(Integer invoiceDetailId, Boolean isUsed) {
        return usedServiceRepo.findAllByInvoiceDetailIdAndIsUsed(invoiceDetailId, isUsed);
    }

    @Override
    public void delete(Integer id) {
        try {
            usedServiceRepo.deleteById(id);
        } catch (Exception ex) {
            throw new RuntimeException("Xoá UsedService " + id + " thất bại");
        }
    }

    @Override
    public List<UsedService> updateAll(List<UsedService> usedServices) {
        return usedServiceRepo.saveAll(usedServices);
    }

    @Override
    public UsedService findByServiceRoomIdAndInvoiceDetailIdAndIsUsed(Integer serviceRoomId, Integer invoiceDetailId, Boolean isUsed) {
        Optional<UsedService> optional = usedServiceRepo.findByServiceRoomIdAndInvoiceDetailIdAndIsUsed(serviceRoomId, invoiceDetailId, isUsed);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }
}

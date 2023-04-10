package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.InvoiceDetail;
import com.devz.hotelmanagement.entities.ServiceRoom;
import com.devz.hotelmanagement.entities.UsedService;
import com.devz.hotelmanagement.repositories.UsedServiceRepository;
import com.devz.hotelmanagement.services.InvoiceDetailService;
import com.devz.hotelmanagement.services.ServiceRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devz.hotelmanagement.services.UsedServiceService;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsedServiceServiceImpl implements UsedServiceService {

    @Autowired
    UsedServiceRepository usedServiceRepo;

    @Autowired
    private ServiceRoomService serviceRoomService;

    @Autowired
    private InvoiceDetailService invoiceDetailService;

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
        ServiceRoom serviceRoom = serviceRoomService.findById(usedService.getServiceRoom().getId());
        if (serviceRoom == null) {
            System.out.println(0);
            return null;
        }
        if (!serviceRoom.getStatus()) {
            System.out.println(1);
            return null;
        }
        InvoiceDetail invoiceDetail = invoiceDetailService.findById(usedService.getInvoiceDetail().getId());
        if (invoiceDetail == null) {
            System.out.println(2);
            return null;
        }
        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        LocalDate checkoutExpectedDate = LocalDate.ofInstant(invoiceDetail.getCheckoutExpected().toInstant(), ZoneId.systemDefault());
        LocalDate startedDate = LocalDate.ofInstant(usedService.getStartedTime().toInstant(), ZoneId.systemDefault());
        LocalDate endedDate = LocalDate.ofInstant(usedService.getEndedTime().toInstant(), ZoneId.systemDefault());
        if (startedDate.isBefore(today)) {
            // startedDate < today
            System.out.println(3);
            return null;
        }
        if (endedDate.isAfter(checkoutExpectedDate)) {
            // endedDate > checkoutExpectedDate
            System.out.println(4);
            return null;
        }
        if (startedDate.isAfter(endedDate)) {
            // startedDate > endedDate
            System.out.println(5);
            return null;
        }
        if (!startedDate.isAfter(endedDate) && !startedDate.isBefore(endedDate)) {
            // startedDate == startedDate
            System.out.println(6);
            return null;
        }
        Boolean isUseInRange = usedServiceRepo.existsByServiceRoomIdAndInRangeStartedTimeToEndedTime(
                usedService.getInvoiceDetail().getId(),
                usedService.getServiceRoom().getId(),
                usedService.getStartedTime(),
                usedService.getEndedTime(),
                true
        );
        if (isUseInRange) {
            System.out.println(7);
            return null;
        }
        usedService.setId(null);
        usedService.setStatus(true);
        usedService.setNote("");
        usedService.setServicePrice(serviceRoom.getPrice());
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
    public List<UsedService> findAllByInvoiceDetailIdAndStatus(Integer invoiceDetailId, Boolean status) {
        return usedServiceRepo.findAllByInvoiceDetailIdAndStatus(invoiceDetailId, status);
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
    @Transactional(rollbackFor = { RuntimeException.class })
    public void stop(Integer id) {
        UsedService usedService = this.findById(id);
        if (usedService == null) {
            throw new RuntimeException("Sử dụng dịch vụ không tồn tại!");
        }
        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        LocalDate startedDate = LocalDate.ofInstant(usedService.getStartedTime().toInstant(), ZoneId.systemDefault());
        LocalDate endedDate = LocalDate.ofInstant(usedService.getEndedTime().toInstant(), ZoneId.systemDefault());
        if (!today.isAfter(endedDate) && !today.isBefore(endedDate)) {
            throw new RuntimeException("Sử dụng dịch vụ đã hoàn tất!");
        } else if (!today.isAfter(startedDate) && !today.isBefore(startedDate)) {
            usedService.setStatus(false);
        } else {
            usedService.setEndedTime(new Date());
        }
        if (this.update(usedService) == null) {
            throw new RuntimeException("Cập nhật sử dụng dịch vụ không tồn tại!");
        }
    }

    @Override
    public List<UsedService> updateAll(List<UsedService> usedServices) {
        return usedServiceRepo.saveAll(usedServices);
    }

    @Override
    public UsedService findByServiceRoomIdAndInvoiceDetailIdAndStatus(Integer serviceRoomId, Integer invoiceDetailId, Boolean status) {
        Optional<UsedService> optional = usedServiceRepo.findByServiceRoomIdAndInvoiceDetailIdAndStatus(serviceRoomId, invoiceDetailId, status);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

}

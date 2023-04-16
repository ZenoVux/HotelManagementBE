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
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        if (!serviceRoom.getStatus()) {
            return null;
        }
        InvoiceDetail invoiceDetail = invoiceDetailService.findById(usedService.getInvoiceDetail().getId());
        if (invoiceDetail == null) {
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        LocalDate checkoutExpectedDate = LocalDate.ofInstant(invoiceDetail.getCheckoutExpected().toInstant(), ZoneId.systemDefault());
        LocalDate startedDate = LocalDate.ofInstant(usedService.getStartedTime().toInstant(), ZoneId.systemDefault());
        LocalDate endedDate = LocalDate.ofInstant(usedService.getEndedTime().toInstant(), ZoneId.systemDefault());
        if (startedDate.isBefore(today)) {
            // startedDate < today
            throw new RuntimeException("{\"error\":\"Không thể chọn ngày bắt đầu trước ngày hiện tại!\"}");
        }
        if (endedDate.isAfter(checkoutExpectedDate)) {
            // endedDate > checkoutExpectedDate
            throw new RuntimeException("{\"error\":\"Không thể chọn ngày kết thúc sau ngày trả phòng!\"}");
        }
        if (startedDate.isAfter(endedDate)) {
            // startedDate > endedDate
            throw new RuntimeException("{\"error\":\"Không thể chọn ngày bắt đầu sau ngày kết thúc!\"}");
        }
        if (!startedDate.isAfter(endedDate) && !startedDate.isBefore(endedDate)) {
            // startedDate == startedDate
            throw new RuntimeException("{\"error\":\"Không thể chọn ngày bắt đầu trùng ngày kết thúc!\"}");
        }
        Boolean isUseInRange = usedServiceRepo.existsByServiceRoomIdAndInRangeStartedTimeToEndedTime(
                usedService.getInvoiceDetail().getId(),
                usedService.getServiceRoom().getId(),
                usedService.getStartedTime(),
                usedService.getEndedTime(),
                true
        );
        if (isUseInRange) {
            throw new RuntimeException("{\"error\":\"Dịch vụ đang được sử dụng!\"}");
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
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
    }

    @Override
    @Transactional(rollbackFor = { RuntimeException.class })
    public void stop(Integer id) {
        UsedService usedService = this.findById(id);
        if (usedService == null) {
            // Sử dụng dịch vụ không tồn tại!
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        LocalDate startedDate = LocalDate.ofInstant(usedService.getStartedTime().toInstant(), ZoneId.systemDefault());
        LocalDate endedDate = LocalDate.ofInstant(usedService.getEndedTime().toInstant(), ZoneId.systemDefault());
        if (!today.isAfter(endedDate) && !today.isBefore(endedDate)) {
            // Sử dụng dịch vụ đã hoàn tất!
            throw new RuntimeException("{\"error\":\"Sử dụng dịch vụ đã hoàn tất!\"}");
        } else if (!today.isAfter(startedDate) && !today.isBefore(startedDate)) {
            usedService.setStatus(false);
        } else {
            usedService.setEndedTime(new Date());
        }
        if (this.update(usedService) == null) {
            // Cập nhật sử dụng dịch vụ không tồn tại!
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
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

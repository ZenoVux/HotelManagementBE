package com.devz.hotelmanagement.services.impl;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.devz.hotelmanagement.entities.Customer;
import com.devz.hotelmanagement.entities.InvoiceDetail;
import com.devz.hotelmanagement.entities.Room;
import com.devz.hotelmanagement.services.CustomerService;
import com.devz.hotelmanagement.services.InvoiceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devz.hotelmanagement.entities.HostedAt;
import com.devz.hotelmanagement.repositories.HostedAtRepository;
import com.devz.hotelmanagement.services.HostedAtService;

@Service
public class HostedAtServiceImpl implements HostedAtService {

    @Autowired
    private HostedAtRepository hostedAtRepo;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private InvoiceDetailService invoiceDetailService;

    @Override
    public List<HostedAt> findAll() {
        return hostedAtRepo.findAll();
    }

    @Override
    public HostedAt findById(int id) {
        Optional<HostedAt> optional = hostedAtRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public HostedAt findByCode(String code) {
        return null;
    }

    @Override
    public HostedAt create(HostedAt hostedAt) {
        Customer customer = customerService.findById(hostedAt.getCustomer().getId());
        if (customer == null) {
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        InvoiceDetail invoiceDetail = invoiceDetailService.findById(hostedAt.getInvoiceDetail().getId());
        if (invoiceDetail == null) {
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        List<HostedAt> hostedAts = this.findByInvoiceDetailId(invoiceDetail.getId());
        LocalDate today = LocalDate.now();
        long numAdults = hostedAts.stream().filter(_hostedAt -> {
            LocalDate birthdate = LocalDate.ofInstant(_hostedAt.getCustomer().getDateOfBirth().toInstant(), ZoneId.systemDefault());
            int age = Period.between(birthdate, today).getYears();
            return age >= 13;
        }).count();
        long numChilds = hostedAts.stream().filter(_hostedAt -> {
            LocalDate birthdate = LocalDate.ofInstant(_hostedAt.getCustomer().getDateOfBirth().toInstant(), ZoneId.systemDefault());
            int age = Period.between(birthdate, today).getYears();
            return age < 13;
        }).count();
        LocalDate birthdate = LocalDate.ofInstant(customer.getDateOfBirth().toInstant(), ZoneId.systemDefault());
        int age = Period.between(birthdate, today).getYears();

        Room room = invoiceDetail.getRoom();
        if (age >= 13 && numAdults >= (room.getRoomType().getNumAdults() + room.getRoomType().getMaxAdultsAdd())) {
            // không thể thêm người lớn vào phòng này. số lượng đạt tối đa
            throw new RuntimeException("{\"error\":\"Không thể thêm người lớn vào phòng này. Số lượng đạt tối đa!\"}");
        }
        if (age < 13 && numChilds >= (room.getRoomType().getNumChilds() + room.getRoomType().getMaxChildsAdd())) {
            // không thể thêm trẻ em vào phòng này. số lượng đạt tối đa
            throw new RuntimeException("{\"error\":\"Không thể thêm trẻ em vào phòng này. Số lượng đạt tối đa!\"}");
        }
        hostedAt.setId(null);
        hostedAt.setCheckin(new Date());
        return hostedAtRepo.save(hostedAt);
    }

    @Override
    public HostedAt update(HostedAt hostedAt) {
        return hostedAtRepo.save(hostedAt);
    }

    @Override
    public void delete(Integer id) {
        HostedAt hostedAt = this.findById(id);
        if (hostedAt == null) {
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        InvoiceDetail invoiceDetail = hostedAt.getInvoiceDetail();
        List<HostedAt> hostedAts = this.findByInvoiceDetailId(invoiceDetail.getId());
        LocalDate today = LocalDate.now();
        long numAdults = hostedAts.stream().filter(_hostedAt -> {
            LocalDate birthdate = LocalDate.ofInstant(_hostedAt.getCustomer().getDateOfBirth().toInstant(), ZoneId.systemDefault());
            int age = Period.between(birthdate, today).getYears();
            return age >= 13;
        }).count();
        if (numAdults <= 1) {
            throw new RuntimeException("{\"error\":\"Phải có ít nhất 1 người lớn trong phòng!\"}");
        }
        hostedAtRepo.deleteById(id);
    }

    @Override
    public List<HostedAt> updateAll(List<HostedAt> hostedAts) {
        return hostedAtRepo.saveAll(hostedAts);
    }

    @Override
    public List<HostedAt> findByInvoiceDetailId(Integer id) {
        return hostedAtRepo.findByInvoiceDetailId(id);
    }
}

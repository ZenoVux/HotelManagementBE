package com.devz.hotelmanagement.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devz.hotelmanagement.entities.HostedAt;
import com.devz.hotelmanagement.repositories.HostedAtRepository;
import com.devz.hotelmanagement.services.HostedAtService;

@Service
public class HostedAtServiceImpl implements HostedAtService {

    @Autowired
    HostedAtRepository hostedAtRepo;

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
        hostedAt.setId(null);
        try {
            String maxCode = hostedAtRepo.getMaxCode();
            Integer index = 1;
            if (maxCode != null) {
                index = Integer.parseInt(maxCode.replace("HA", ""));
                index++;
            }
            String code = String.format("HA%05d", index);
            hostedAt.setCode(code);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return hostedAtRepo.save(hostedAt);
    }

    @Override
    public HostedAt update(HostedAt hostedAt) {
        return hostedAtRepo.save(hostedAt);
    }

    @Override
    public void delete(Integer id) {
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

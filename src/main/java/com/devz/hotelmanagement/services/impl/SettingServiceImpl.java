package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.Setting;
import com.devz.hotelmanagement.repositories.SettingRepository;
import com.devz.hotelmanagement.services.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingServiceImpl implements SettingService {

    @Autowired
    private SettingRepository settingRepo;

    @Override
    public Setting getSetting() {
        return settingRepo.getSetting();
    }

    @Override
    public Setting update(Setting setting) {
        return settingRepo.save(setting);
    }

}

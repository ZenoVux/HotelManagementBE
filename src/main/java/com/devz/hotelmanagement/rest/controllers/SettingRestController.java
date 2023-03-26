package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.Setting;
import com.devz.hotelmanagement.services.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/settings")
public class SettingRestController {

    @Autowired
    private SettingService settingService;

    @GetMapping
    private Setting getSetting() {
        return settingService.getSetting();
    }

    @PostMapping
    private Setting update(@RequestBody Setting setting) {
        return settingService.update(setting);
    }

}

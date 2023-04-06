package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/storage")
public class StorageRestController {

    @Autowired
    private StorageService storageService;

    @GetMapping
    public List<String> getAllFiles() {
        return storageService.listAllFiles();
    }

    @PostMapping
    public String upload(@RequestParam("file") MultipartFile file) {
        storageService.saveFile(file);
        return file.getOriginalFilename();
    }

    @DeleteMapping("/{fileName}")
    public void delete(@PathVariable("fileName") String fileName) {
        storageService.deleteFile(fileName);
    }

    @GetMapping("/get-url/{fileName}")
    public String getPresignedURL(@PathVariable("fileName") String fileName) {
        return storageService.getPresignedURL(fileName);
    }
}

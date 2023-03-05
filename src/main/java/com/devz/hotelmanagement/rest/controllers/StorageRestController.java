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

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        System.out.println("Upload file: " + file.getOriginalFilename());
        System.out.println("uploading...");
        storageService.saveFile(file);
        System.out.println("successfully!");
        return file.getOriginalFilename();
    }

    @GetMapping("/delete/{fileName}")
    public void delete(@PathVariable("fileName") String fileName) {
        storageService.deleteFile(fileName);
    }

    @GetMapping("/list")
    public List<String> getAllFiles() {
        return storageService.listAllFiles();
    }

    @GetMapping("/get-url/{fileName}")
    public String getPresignedURL(@PathVariable("fileName") String fileName) {
        String url = storageService.getPresignedURL(fileName);
        return url;
    }
}

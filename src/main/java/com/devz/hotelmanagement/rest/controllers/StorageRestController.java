package com.devz.hotelmanagement.rest.controllers;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.devz.hotelmanagement.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/storage")
public class StorageRestController {

    @Autowired
    private StorageService storageService;

    @Value("${bucketName}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3;

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

    @GetMapping("/{fileName}")
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable("fileName") String fileName) {
        S3Object s3Object = s3.getObject(bucketName, fileName);

        S3ObjectInputStream stream = s3Object.getObjectContent();

        ByteArrayResource resource;
        try {
            resource = new ByteArrayResource(IOUtils.toByteArray(stream));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(s3Object.getObjectMetadata().getContentLength());
        headers.setContentType(MediaType.parseMediaType(s3Object.getObjectMetadata().getContentType()));
        headers.setCacheControl("max-age=31536000");

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    @GetMapping("/get-url/{fileName}")
    public String getPresignedURL(@PathVariable("fileName") String fileName) {
        return storageService.getPresignedURL(fileName);
    }
}

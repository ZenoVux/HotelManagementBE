package com.devz.hotelmanagement.rest.controllers;

import java.io.IOException;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/images")
public class ImageRestController {

    @Value("${bucketName}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3;

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
}

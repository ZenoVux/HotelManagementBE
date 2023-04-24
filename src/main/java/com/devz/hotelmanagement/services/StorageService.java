package com.devz.hotelmanagement.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface StorageService {

    String saveFile(MultipartFile file);

    String deleteFile(String filename);

    List<String> listAllFiles();

    String getPresignedURL(String fileName);

    String saveFile2(InputStream inputStream, String fileName);
}

package com.devz.hotelmanagement.services.impl;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.devz.hotelmanagement.services.StorageService;
import jakarta.activation.MimetypesFileTypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class S3StorageServiceImpl implements StorageService {

    @Value("${bucketName}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3;

    public boolean validateFileImage(File file) {
        String mimetype = new MimetypesFileTypeMap().getContentType(file);
        System.out.println(mimetype);
        if (!mimetype.startsWith("image/")) {
            return false;
        }

        long fileSizeInBytes = file.length();
        long fileSizeInKB = fileSizeInBytes / 1024;
        long fileSizeInMB = fileSizeInKB / 1024;
        if (fileSizeInMB > 20) {
            return false;
        }

        return true;
    }

    @Override
    public String saveFile(MultipartFile multipartFile) {
        String originalFilename = System.currentTimeMillis() + "-" + multipartFile.getOriginalFilename();
        try {
            File fileConvert = converMultiPartToFile(multipartFile);
            if (!validateFileImage(fileConvert)) {
                return null;
            }
            s3.putObject(bucketName, originalFilename, fileConvert);
            return originalFilename;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * Xóa file trên s3
     */
    @Override
    public String deleteFile(String filename) {
        s3.deleteObject(bucketName, filename);
        return "File deleted";
    }

    /*
     * Trả về list file trong bucket
     */
    @Override
    public List<String> listAllFiles() {
        ListObjectsV2Result listObjectsV2Result = s3.listObjectsV2(bucketName);
        return listObjectsV2Result.getObjectSummaries().stream().map(S3ObjectSummary::getKey).collect(Collectors.toList());
    }

    /*
     * converMultiPartToFile
     */
    private File converMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    // Creating an AWS S3 Presigned URL
    @Override
    public String getPresignedURL(String fileName) {
//		// Đặt URL được chỉ định trước để hết hạn sau một giờ
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60;
        expiration.setTime(expTimeMillis);

        // Tạo link
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, fileName)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);
        URL url = s3.generatePresignedUrl(generatePresignedUrlRequest);

        return url.toString();

    }
}

package com.hanghae.knowledgesharing.common.util.s3.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.hanghae.knowledgesharing.common.util.s3.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;


@Service
public class FileServiceImpl implements FileService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Autowired
    public FileServiceImpl(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String fileExtension = Objects.requireNonNull(originalFilename)
                .contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
        String keyName = UUID.randomUUID().toString() + fileExtension;

        try {
            amazonS3.putObject(new PutObjectRequest(bucketName, keyName, file.getInputStream(), null));
        } catch (IOException e) {
            throw new RuntimeException("Error in uploading file", e);
        }

        return "http://localhost:4040/api/files/" + keyName;
    }

    @Override
    public InputStreamResource downloadFile(String filename) {
        S3Object s3Object = amazonS3.getObject(bucketName, filename);
        return new InputStreamResource(s3Object.getObjectContent());
    }
}



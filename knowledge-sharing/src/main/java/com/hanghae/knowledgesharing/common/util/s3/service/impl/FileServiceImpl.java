package com.hanghae.knowledgesharing.common.util.s3.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hanghae.knowledgesharing.common.entity.User;
import com.hanghae.knowledgesharing.common.exception.CustomException;
import com.hanghae.knowledgesharing.common.exception.ErrorCode;
import com.hanghae.knowledgesharing.common.util.s3.service.FileService;
import com.hanghae.knowledgesharing.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;


@Service
public class FileServiceImpl implements FileService {

    private final AmazonS3 amazonS3;
    private final UserRepository userRepository;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    String region = "ap-northeast-2";

    @Autowired
    public FileServiceImpl(AmazonS3 amazonS3, UserRepository userRepository) {
        this.amazonS3 = amazonS3;
        this.userRepository = userRepository;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String fileExtension = Objects.requireNonNull(originalFilename)
                .contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
        String folderPath = "user/"; // 예: "documents/reports/"
        String keyName = folderPath +UUID.randomUUID().toString() + fileExtension;

        try {
            amazonS3.putObject(new PutObjectRequest(bucketName, keyName, file.getInputStream(), null));
        } catch (IOException e) {
            throw new RuntimeException("Error in uploading file", e);
        }

        String s3Url = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, keyName);
        return s3Url;

    }

    @Override
    public String uploadFileArticles(MultipartFile file, String userId) {
        User user = userRepository.findByUserId(userId);
        if(user == null){
            throw new CustomException(ErrorCode.UserNotFound);
        };

        String originalFilename = file.getOriginalFilename();
        String fileExtension = Objects.requireNonNull(originalFilename)
                .contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
        String folderPath = "articles/"; // 예: "documents/reports/"
        String keyName = folderPath +UUID.randomUUID().toString() + fileExtension;

        try {
            amazonS3.putObject(new PutObjectRequest(bucketName, keyName, file.getInputStream(), null));
        } catch (IOException e) {
            throw new RuntimeException("Error in uploading file", e);
        }

        String s3Url = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, keyName);
        return s3Url;

    }
}



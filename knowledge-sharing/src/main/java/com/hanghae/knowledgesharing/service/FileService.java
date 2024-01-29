package com.hanghae.knowledgesharing.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadFile(MultipartFile file);
    InputStreamResource downloadFile(String filename);
}

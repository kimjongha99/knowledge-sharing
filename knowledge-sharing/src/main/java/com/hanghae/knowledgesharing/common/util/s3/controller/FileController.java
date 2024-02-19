package com.hanghae.knowledgesharing.common.util.s3.controller;

import com.hanghae.knowledgesharing.common.util.s3.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {
    private  final FileService fileService;



    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileUrl = fileService.uploadFile(file);
        return ResponseEntity.ok(fileUrl);
    }
    @PostMapping("/upload/article")
    public ResponseEntity<String> uploadFileArticle(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal String userId) {
        String fileUrl = fileService.uploadFileArticles(file , userId);
        return ResponseEntity.ok(fileUrl);
    }
}

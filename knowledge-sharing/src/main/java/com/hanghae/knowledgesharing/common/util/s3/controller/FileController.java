package com.hanghae.knowledgesharing.common.util.s3.controller;

import com.hanghae.knowledgesharing.common.util.s3.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/{filename}")
    public ResponseEntity<InputStreamResource> viewImage(@PathVariable String filename) {
        InputStreamResource resource = fileService.downloadFile(filename);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }
}

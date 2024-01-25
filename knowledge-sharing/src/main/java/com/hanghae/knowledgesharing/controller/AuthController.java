package com.hanghae.knowledgesharing.controller;


import com.hanghae.knowledgesharing.dto.request.auth.CheckCertificationRequestDto;
import com.hanghae.knowledgesharing.dto.request.auth.EmailCertificationRequestDto;
import com.hanghae.knowledgesharing.dto.request.auth.IdCheckRequestDto;
import com.hanghae.knowledgesharing.dto.response.auth.CheckCertificationResponseDto;
import com.hanghae.knowledgesharing.dto.response.auth.EmailCertificationResponseDto;
import com.hanghae.knowledgesharing.dto.response.auth.IdCheckResponseDto;
import com.hanghae.knowledgesharing.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/id-check")
    public ResponseEntity<? super IdCheckResponseDto> idCheck(
            @RequestBody @Valid IdCheckRequestDto requestBody
    ) {
        ResponseEntity<? super IdCheckResponseDto> response = authService.idCheck(requestBody);
        return response;
    }
    @PostMapping("/email-certification")
    public ResponseEntity<? super EmailCertificationResponseDto> emailCertification(
            @RequestBody @Valid EmailCertificationRequestDto requestBody
    ) {
        ResponseEntity<? super EmailCertificationResponseDto> response = authService.emailCertification(requestBody);
        return response;
    }



    @PostMapping("/check-certification")
    public ResponseEntity<? super CheckCertificationResponseDto> checkCertification(
            @RequestBody @Valid CheckCertificationRequestDto requestBody
    ) {
        ResponseEntity<? super CheckCertificationResponseDto> response = authService.emailCertificationCheck(requestBody);
        return response;
    }

}

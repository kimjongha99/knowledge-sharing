package com.hanghae.knowledgesharing.auth.controller;


import com.hanghae.knowledgesharing.auth.dto.request.*;
import com.hanghae.knowledgesharing.auth.service.AuthService;
import com.hanghae.knowledgesharing.auth.dto.response.auth.*;
import jakarta.servlet.http.HttpServletResponse;
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

    @PostMapping("/sign-up")
    public ResponseEntity<? super SignUpResponseDto> signUp (
            @RequestBody @Valid SignUpRequestDto requestBody
    ) {
        ResponseEntity<? super SignUpResponseDto> response = authService.signUp(requestBody);
        return response;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<? super SignInResponseDto> signIn (
            @RequestBody @Valid SignInRequestDto requestBody,
            HttpServletResponse response

    ) {
        ResponseEntity<? super SignInResponseDto> result = authService.signIn(requestBody,response);
        return result;
    }

    @PostMapping("/refresh")
    public  ResponseEntity<? super RefreshResponseDto> refreshAccessToken(
            @RequestBody@Valid RefreshRequestDto requestBody,
            HttpServletResponse response
    ) {
        ResponseEntity<? super RefreshResponseDto> result = authService.refreshAccessToken(requestBody , response);
        return result;
    }

}












package com.hanghae.knowledgesharing.auth.service;


import com.hanghae.knowledgesharing.auth.dto.request.*;
import com.hanghae.knowledgesharing.auth.dto.response.auth.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckRequestDto idCheckRequestDto); //리스폰스엔트티로 반환할건데 IdCheckResponseDto의 부모 도 가져올수있게. 매개변수는 request
    ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto);

    ResponseEntity<? super CheckCertificationResponseDto> emailCertificationCheck(CheckCertificationRequestDto checkCertificationRequestDto);


    ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);

    ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto, HttpServletResponse response);

    ResponseEntity<? super RefreshResponseDto> refreshAccessToken(RefreshRequestDto requestBody, HttpServletResponse response);
}

package com.hanghae.knowledgesharing.service;


import com.hanghae.knowledgesharing.dto.request.auth.*;
import com.hanghae.knowledgesharing.dto.response.auth.*;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckRequestDto idCheckRequestDto); //리스폰스엔트티로 반환할건데 IdCheckResponseDto의 부모 도 가져올수있게. 매개변수는 request
    ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto);

    ResponseEntity<? super CheckCertificationResponseDto> emailCertificationCheck(CheckCertificationRequestDto checkCertificationRequestDto);


    ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);

    ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto);

}

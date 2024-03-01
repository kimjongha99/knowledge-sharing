package com.hanghae.knowledgesharing.auth.service;


import com.hanghae.knowledgesharing.auth.dto.request.auth.*;
import com.hanghae.knowledgesharing.auth.dto.response.auth.RefreshResponseDto;
import com.hanghae.knowledgesharing.auth.dto.response.auth.SignInResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.transaction.annotation.Transactional;

public interface AuthService {
    @Transactional
    String idChecking(IdCheckRequestDto requestDto);

    @Transactional
    String emailCertification(EmailCertificationRequestDto dto);

    // 이메일 인증번호 검증
    @Transactional
    String emailCertificationCheck(CheckCertificationRequestDto dto);

    String signUp(SignUpRequestDto dto);


    @Transactional
    SignInResponseDto signIn(SignInRequestDto dto, HttpServletResponse response);

    //    //1. RefreshRequestDto 에서 리프래쉬토큰을 가져온다.
    //    //2. jwtProvider에 리프래쉬 토큰 검증 메서드를 만들고 검증한다.
    //    //3. 리프래쉬토큰이 만료된 토큰이거나 잘못된토큰이면 refreshInExpired 이 에러를 날린다.
    //    //4. 그게아닌 다른이유면 REFRESH_TOKEN_INVALID 이 애러를 날린다
    //    //5. 둘다 검증후 아니면 새로운 액세스토큰을 발급한다.
    //    //6.     public static ResponseEntity<RefreshResponseDto> success(String newAccessToken) { 여기에 담아서 리턴한다.
     RefreshResponseDto refreshAccessToken(RefreshRequestDto requestBody,HttpServletResponse response);




}

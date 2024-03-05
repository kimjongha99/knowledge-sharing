package com.hanghae.knowledgesharing.auth.controller;


import com.hanghae.knowledgesharing.auth.dto.request.auth.*;
import com.hanghae.knowledgesharing.auth.dto.response.auth.RefreshResponseDto;
import com.hanghae.knowledgesharing.auth.dto.response.auth.SignInResponseDto;
import com.hanghae.knowledgesharing.auth.service.AuthService;
import com.hanghae.knowledgesharing.common.dto.ResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

//    @Operation(summary = "아이디 중복검사 ", description = "아이디를 중복검사할수있습니다.")
    @PostMapping("/id-check")
    public ResponseDto<String> idCheck(
            @RequestBody @Valid IdCheckRequestDto requestBody
    ) {
         String result =  authService.idChecking(requestBody);
         return ResponseDto.success(result);
    }


//    @Operation(summary = "이메일 인증 ", description = "이메일 인증 번호 전송입니다..")
       @PostMapping("/email-certification")
       public ResponseDto<String> emailCertification(
               @RequestBody @Valid EmailCertificationRequestDto requestBody
       ){
           String result =  authService.emailCertification(requestBody);

           return ResponseDto.success(result);
       }


    @PostMapping("/check-certification")
    public ResponseDto<String> checkCertification(
            @RequestBody @Valid CheckCertificationRequestDto requestBody
    ) {
        String  result = authService.emailCertificationCheck(requestBody);
        return ResponseDto.success(result);
    }


    @PostMapping("/sign-up")
    public ResponseDto<String> signUp(@RequestBody @Valid SignUpRequestDto requestBody) {
        String  result= authService.signUp(requestBody);
        return ResponseDto.success(result);

    }



    // 로그인 요청을 처리합니다.
    @PostMapping("/sign-in")
    public ResponseDto<SignInResponseDto> signIn(@RequestBody @Valid SignInRequestDto requestBody, HttpServletResponse response) throws UnsupportedEncodingException {
        SignInResponseDto  result = authService.signIn(requestBody,response);

        return ResponseDto.success(result);
    }




    @PostMapping("/refresh")
    public  ResponseDto<RefreshResponseDto> refreshAccessToken(
            @RequestBody@Valid RefreshRequestDto requestBody,
            HttpServletResponse response
    ) {
        RefreshResponseDto result = authService.refreshAccessToken(requestBody,response);
        return ResponseDto.success(result);
    }

}












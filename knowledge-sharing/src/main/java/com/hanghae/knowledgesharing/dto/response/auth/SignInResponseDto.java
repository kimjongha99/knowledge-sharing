package com.hanghae.knowledgesharing.dto.response.auth;

import com.hanghae.knowledgesharing.common.ResponseCode;
import com.hanghae.knowledgesharing.common.ResponseMessage;
import com.hanghae.knowledgesharing.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class SignInResponseDto extends  ResponseDto{
    private String accessToken;
    private String refreshToken;

    private int expirationTime;

    private SignInResponseDto(String accessToken, String refreshToken, int expirationTime) {
        super();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expirationTime = expirationTime;
    }

    public static ResponseEntity<SignInResponseDto> success(String accessToken, String refreshToken, int expirationTime) {
        SignInResponseDto responseBody = new SignInResponseDto(accessToken, refreshToken, expirationTime);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> signInFail() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.SIGN_IN_FAIL, ResponseMessage.SIGN_IN_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }

}
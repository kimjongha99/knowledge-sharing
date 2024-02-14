package com.hanghae.knowledgesharing.auth.dto.response.auth;

import com.hanghae.knowledgesharing.common.exception.ResponseCode;
import com.hanghae.knowledgesharing.common.exception.ResponseMessage;
import com.hanghae.knowledgesharing.common.dto.ResponseDto;
import com.hanghae.knowledgesharing.common.enums.UserRoleEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class SignInResponseDto extends  ResponseDto{

    private UserRoleEnum role;
    private String accessToken;
    private String refreshToken;
    private int expirationTime;

    private SignInResponseDto(String accessToken, String refreshToken, int expirationTime , UserRoleEnum role) {
        super();

        this.accessToken = accessToken;
        this.refreshToken = refreshToken;

        this.expirationTime = expirationTime;
        this.role =role;
    }

    public static ResponseEntity<SignInResponseDto> success(String accessToken, String refreshToken, int expirationTime, UserRoleEnum role) {
        SignInResponseDto responseBody = new SignInResponseDto(accessToken, refreshToken, expirationTime, role);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> signInFail() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.SIGN_IN_FAIL, ResponseMessage.SIGN_IN_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }

}
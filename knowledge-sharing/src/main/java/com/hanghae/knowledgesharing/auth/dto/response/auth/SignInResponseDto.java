package com.hanghae.knowledgesharing.auth.dto.response.auth;

import lombok.Getter;


@Getter
public class SignInResponseDto  {
    private String accessToken;
    private String refreshToken;

    private int expirationTime;

    public SignInResponseDto(String accessToken, String refreshToken, int expirationTime) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expirationTime = expirationTime;
    }

}
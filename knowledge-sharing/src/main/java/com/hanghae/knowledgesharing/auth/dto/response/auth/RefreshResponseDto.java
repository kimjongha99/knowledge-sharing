package com.hanghae.knowledgesharing.auth.dto.response.auth;


import lombok.Getter;

@Getter
public class RefreshResponseDto  {

    private String newAccessToken;

    private  int expirationTime;

    public RefreshResponseDto(String newAccessToken) {
        this.newAccessToken=  newAccessToken;
        this.expirationTime=3600;
    }

}

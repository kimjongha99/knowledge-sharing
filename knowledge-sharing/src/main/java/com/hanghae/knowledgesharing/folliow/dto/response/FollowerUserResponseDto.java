package com.hanghae.knowledgesharing.folliow.dto.response;

import lombok.Getter;

@Getter
public class FollowerUserResponseDto {
    private String userId;
    private String email;

    public FollowerUserResponseDto(String userId, String email) {
        this.userId = userId;
        this.email = email;
    }
}
package com.hanghae.knowledgesharing.admin.dto.response;

import lombok.Getter;

@Getter

public class UserRoleChangeResponseDto {
    private String message;

    public UserRoleChangeResponseDto(String message) {
        this.message = message;
    }
}

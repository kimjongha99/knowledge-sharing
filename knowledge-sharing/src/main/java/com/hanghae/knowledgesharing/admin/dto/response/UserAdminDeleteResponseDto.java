package com.hanghae.knowledgesharing.admin.dto.response;

import lombok.Getter;

@Getter
public class UserAdminDeleteResponseDto {
    private String message;


    public UserAdminDeleteResponseDto(String message) {
        this.message = message;
    }
}

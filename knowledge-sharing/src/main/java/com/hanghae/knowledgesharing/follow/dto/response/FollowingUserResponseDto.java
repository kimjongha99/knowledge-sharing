package com.hanghae.knowledgesharing.follow.dto.response;


import lombok.Getter;

@Getter

public class FollowingUserResponseDto {

    private String userId;
    private String email;


    public FollowingUserResponseDto(String userId, String email) {
        this.userId = userId;
        this.email = email;
    }
}

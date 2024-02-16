package com.hanghae.knowledgesharing.user.dto.response;


import com.hanghae.knowledgesharing.common.entity.User;
import lombok.Getter;

@Getter
public class GetUserResponseDto {
    private String userId;
    private String email;
    private String profileImageUrl;
    private String type;

    private GetUserResponseDto(String userId, String email, String profileImageUrl, String type) {
        this.userId = userId;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.type = type;
    }

    public static GetUserResponseDto fromUser(User user) {
        return new GetUserResponseDto(
                user.getUserId(),
                user.getEmail(),
                user.getProfileImageUrl(),
                user.getType()
        );
    }
}
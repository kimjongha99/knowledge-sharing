package com.hanghae.knowledgesharing.user.dto.response;

import com.hanghae.knowledgesharing.common.entity.User;
import com.hanghae.knowledgesharing.common.enums.UserRoleEnum;
import lombok.Getter;

@Getter
public class GetSignInUserResponseDto {
    private String userId;
    private String email;
    private String profileImageUrl;
    private UserRoleEnum role;
    private String type;

    // 생성자
    private GetSignInUserResponseDto(String userId, String email, String profileImageUrl, UserRoleEnum role, String type) {
        this.userId = userId;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.role = role;
        this.type = type;
    }
    // User 엔티티로부터 GetSignInUserResponseDto 객체를 생성하고 반환하는 정적 메소드
    public static GetSignInUserResponseDto fromUser(User user) {
        return new GetSignInUserResponseDto(
                user.getUserId(),
                user.getEmail(),
                user.getProfileImageUrl(),
                user.getRole(),
                user.getType());
    }
}

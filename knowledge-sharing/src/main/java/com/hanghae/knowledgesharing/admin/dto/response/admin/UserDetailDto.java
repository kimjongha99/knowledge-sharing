package com.hanghae.knowledgesharing.admin.dto.response.admin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDetailDto {
    private String userId;
    private String email;
    private String role;
    private String type; // Assuming 'type' is a relevant user property to include

    public UserDetailDto(String userId, String email, String role, String type) {
        this.userId = userId;
        this.email = email;
        this.role = role;
        this.type = type;
    }
}
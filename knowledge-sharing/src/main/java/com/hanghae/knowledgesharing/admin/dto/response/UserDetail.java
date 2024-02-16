package com.hanghae.knowledgesharing.admin.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class UserDetail {
    private String userId;
    private String email;
    private String role;
    private String type; // Assuming 'type' is a relevant user property to include

    public UserDetail(String userId, String email, String role, String type) {
        this.userId = userId;
        this.email = email;
        this.role = role;
        this.type = type;
    }
}

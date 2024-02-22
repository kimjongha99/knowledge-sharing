package com.hanghae.knowledgesharing.admin.dto.response;

import com.hanghae.knowledgesharing.common.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public  class UserInfo {
    private String userId;
    private String email;
    private String type; // e.g., "app", "social"
    private String profileImageUrl;
    private UserRoleEnum role;
}

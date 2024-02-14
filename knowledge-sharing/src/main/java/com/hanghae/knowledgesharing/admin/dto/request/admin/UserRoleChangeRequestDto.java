package com.hanghae.knowledgesharing.admin.dto.request.admin;


import com.hanghae.knowledgesharing.common.enums.UserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRoleChangeRequestDto {
    private String changeUserId;
    private UserRoleEnum roleEnum;

}

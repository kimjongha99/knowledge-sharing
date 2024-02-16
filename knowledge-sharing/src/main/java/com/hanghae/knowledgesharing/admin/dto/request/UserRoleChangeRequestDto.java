package com.hanghae.knowledgesharing.admin.dto.request;

import com.hanghae.knowledgesharing.common.enums.UserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@NoArgsConstructor
public class UserRoleChangeRequestDto {
    private String changeUserId;
    private UserRoleEnum roleEnum;

}

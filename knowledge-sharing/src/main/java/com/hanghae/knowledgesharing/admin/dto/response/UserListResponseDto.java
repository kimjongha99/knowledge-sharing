package com.hanghae.knowledgesharing.admin.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor
public class UserListResponseDto {


    private Page<UserDetail> users;


    public UserListResponseDto(Page<UserDetail> users) {
        this.users = users;
    }
}

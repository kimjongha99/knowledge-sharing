package com.hanghae.knowledgesharing.admin.dto.response.admin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@NoArgsConstructor
public class UserListResponseDto {
    private Page<UserDetailDto> users;

    public UserListResponseDto(Page<UserDetailDto> users) {
        this.users = users;
    }

    public static ResponseEntity<UserListResponseDto> success(Page<UserDetailDto> users) {
        UserListResponseDto result = new UserListResponseDto(users);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


}
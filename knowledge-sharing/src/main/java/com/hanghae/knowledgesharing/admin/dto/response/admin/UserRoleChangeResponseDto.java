package com.hanghae.knowledgesharing.admin.dto.response.admin;

import com.hanghae.knowledgesharing.common.exception.ResponseCode;
import com.hanghae.knowledgesharing.common.exception.ResponseMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@NoArgsConstructor
public class UserRoleChangeResponseDto {

    private String code;
    private String message;


    public UserRoleChangeResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResponseEntity<UserRoleChangeResponseDto> success() {
        UserRoleChangeResponseDto response = new UserRoleChangeResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    public static ResponseEntity<UserRoleChangeResponseDto> databaseError() {
        UserRoleChangeResponseDto response = new UserRoleChangeResponseDto(ResponseCode.DATABASE_ERROR, ResponseMessage.DATABASE_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }


    public static ResponseEntity<UserRoleChangeResponseDto> noExistUser() {
        UserRoleChangeResponseDto response = new UserRoleChangeResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }
}

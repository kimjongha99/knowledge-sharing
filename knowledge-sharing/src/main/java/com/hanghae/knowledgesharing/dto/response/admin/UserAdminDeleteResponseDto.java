package com.hanghae.knowledgesharing.dto.response.admin;


import com.hanghae.knowledgesharing.common.ResponseCode;
import com.hanghae.knowledgesharing.common.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class UserAdminDeleteResponseDto {

    private String code;
    private String message;

    public UserAdminDeleteResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
    }


    public static ResponseEntity<UserAdminDeleteResponseDto> success() {
        UserAdminDeleteResponseDto response = new UserAdminDeleteResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    public static ResponseEntity<UserAdminDeleteResponseDto> databaseError() {
        UserAdminDeleteResponseDto response = new UserAdminDeleteResponseDto(ResponseCode.DATABASE_ERROR, ResponseMessage.DATABASE_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

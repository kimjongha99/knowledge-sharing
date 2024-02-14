package com.hanghae.knowledgesharing.follow.dto.response.follow;


import com.hanghae.knowledgesharing.common.exception.ResponseCode;
import com.hanghae.knowledgesharing.common.exception.ResponseMessage;
import com.hanghae.knowledgesharing.common.enums.CheckFollowEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class CheckFollowResponseDto {

    private String code;
    private String message;

    private CheckFollowEnum checkFollowEnum;


    //팔로우 성공 에대한 생성자
    public CheckFollowResponseDto(String code, String message, CheckFollowEnum checkFollowEnum) {
        this.code = code;
        this.message = message;
        this.checkFollowEnum = checkFollowEnum;
    }

    // 팔로우 실패에 대한 생성자
    public CheckFollowResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
    }


    public static ResponseEntity<CheckFollowResponseDto> success(CheckFollowEnum checkFollowEnum) {
        CheckFollowResponseDto response = new CheckFollowResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS,checkFollowEnum);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    public static ResponseEntity<CheckFollowResponseDto> notExistUser()     {
        CheckFollowResponseDto response = new CheckFollowResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    public static ResponseEntity<CheckFollowResponseDto> databaseError() {
        CheckFollowResponseDto response = new CheckFollowResponseDto(ResponseCode.DATABASE_ERROR, ResponseMessage.DATABASE_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

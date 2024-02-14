package com.hanghae.knowledgesharing.comment.dto.response.comment;


import com.hanghae.knowledgesharing.common.exception.ResponseCode;
import com.hanghae.knowledgesharing.common.exception.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class PatchCommentResponseDto {

    private String code;
    private  String message;

    public PatchCommentResponseDto(String code, String message) {

        this.code = code;
        this.message = message;
    }

    public static ResponseEntity<PatchCommentResponseDto> success() {
        PatchCommentResponseDto response = new PatchCommentResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    public static ResponseEntity<PatchCommentResponseDto> databaseError() {
        PatchCommentResponseDto response = new PatchCommentResponseDto(ResponseCode.DATABASE_ERROR, ResponseMessage.DATABASE_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    public static ResponseEntity<PatchCommentResponseDto> notExistUser()     {
        PatchCommentResponseDto response = new PatchCommentResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
}

    public static ResponseEntity<PatchCommentResponseDto> notAuthorized() {
        PatchCommentResponseDto response = new PatchCommentResponseDto(ResponseCode.PERMISSION_FAIL, ResponseMessage.PERMISSION_FAIL);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);

    }
}

package com.hanghae.knowledgesharing.comment.dto.response.comment;


import com.hanghae.knowledgesharing.common.exception.ResponseCode;
import com.hanghae.knowledgesharing.common.exception.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class DeleteCommentResponseDto {

    private String code;
    private  String message;

    public DeleteCommentResponseDto(String code, String message) {

        this.code = code;
        this.message = message;
    }

    public static ResponseEntity<DeleteCommentResponseDto> success() {
        DeleteCommentResponseDto response = new DeleteCommentResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    public static ResponseEntity<DeleteCommentResponseDto> databaseError() {
        DeleteCommentResponseDto response = new DeleteCommentResponseDto(ResponseCode.DATABASE_ERROR, ResponseMessage.DATABASE_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    public static ResponseEntity<DeleteCommentResponseDto> notExistUser() {
        DeleteCommentResponseDto response = new DeleteCommentResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);

    }
}

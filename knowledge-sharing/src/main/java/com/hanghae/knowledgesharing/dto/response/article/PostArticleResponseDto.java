package com.hanghae.knowledgesharing.dto.response.article;

import com.hanghae.knowledgesharing.common.ResponseCode;
import com.hanghae.knowledgesharing.common.ResponseMessage;
import com.hanghae.knowledgesharing.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@Getter
public class PostArticleResponseDto extends ResponseDto {

    private PostArticleResponseDto() {
        super();
    }

    public static ResponseEntity<PostArticleResponseDto> success(){

        PostArticleResponseDto result =new PostArticleResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    public static ResponseEntity<ResponseDto> notExistUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }
    public static ResponseEntity<ResponseDto> hashtagCreationFail() {
        ResponseDto result = new ResponseDto(ResponseCode.HASHTAG_CREATION_FAIL, ResponseMessage.HASHTAG_CREATION_FAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public static ResponseEntity<ResponseDto> hashtagNotFound() {
        ResponseDto result = new ResponseDto(ResponseCode.HASHTAG_NOT_FOUND, ResponseMessage.HASHTAG_NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    public static ResponseEntity<ResponseDto> hashtagValidationFail() {
        ResponseDto result = new ResponseDto(ResponseCode.HASHTAG_VALIDATION_FAIL, ResponseMessage.HASHTAG_VALIDATION_FAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }


}

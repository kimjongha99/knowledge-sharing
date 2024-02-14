package com.hanghae.knowledgesharing.article.dto.response.article;

import com.hanghae.knowledgesharing.common.exception.ResponseCode;
import com.hanghae.knowledgesharing.common.exception.ResponseMessage;
import com.hanghae.knowledgesharing.common.dto.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@Getter
public class DeleteArticleResponseDto extends ResponseDto {

    private  DeleteArticleResponseDto(){
        super();
    }


    public static ResponseEntity<DeleteArticleResponseDto> success() {
        DeleteArticleResponseDto result = new DeleteArticleResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    public static ResponseEntity<ResponseDto> noExistArticle() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_ARTICLE, ResponseMessage.NOT_EXISTED_ARTICLE);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public static ResponseEntity<ResponseDto> noExistUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public static ResponseEntity<ResponseDto> permissionFail() {
        ResponseDto result = new ResponseDto(ResponseCode.PERMISSION_FAIL, ResponseMessage.PERMISSION_FAIL);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
    }
}

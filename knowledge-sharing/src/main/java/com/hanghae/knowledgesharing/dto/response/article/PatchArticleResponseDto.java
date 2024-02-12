package com.hanghae.knowledgesharing.dto.response.article;


import com.hanghae.knowledgesharing.common.ResponseCode;
import com.hanghae.knowledgesharing.common.ResponseMessage;
import com.hanghae.knowledgesharing.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class PatchArticleResponseDto extends ResponseDto {


    private PatchArticleResponseDto(){
        super();
    }

    public static ResponseEntity<PatchArticleResponseDto> success() {
        PatchArticleResponseDto result = new PatchArticleResponseDto();
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

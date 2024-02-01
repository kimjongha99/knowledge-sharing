package com.hanghae.knowledgesharing.dto.response.article;

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


}

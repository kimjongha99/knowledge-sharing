package com.hanghae.knowledgesharing.dto.response.article;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@NoArgsConstructor

public class ArticleViewCountResponseDto {

    private String code;
    private String message;


    private ArticleViewCountResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public static ResponseEntity<ArticleViewCountResponseDto> success(String code, String message) {
        ArticleViewCountResponseDto response = new ArticleViewCountResponseDto(code, message);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    public static ResponseEntity<ArticleViewCountResponseDto> databaseError(String code, String message) {
        ArticleViewCountResponseDto response = new ArticleViewCountResponseDto(code, message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }



}

package com.hanghae.knowledgesharing.dto.response.admin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@NoArgsConstructor
public class ArticleListResponseDto {


    private Page<ArticleDetailDto> articles;


    public ArticleListResponseDto(Page<ArticleDetailDto> articles) {
        this.articles = articles;
    }



    public static ResponseEntity<ArticleListResponseDto> success(Page<ArticleDetailDto> articles) {
        ArticleListResponseDto result = new ArticleListResponseDto(articles);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }





}

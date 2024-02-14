package com.hanghae.knowledgesharing.admin.dto.response.admin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@NoArgsConstructor
public class AdminArticleListResponseDto {


    private Page<AdminArticleDetailDto> articles;


    public AdminArticleListResponseDto(Page<AdminArticleDetailDto> articles) {
        this.articles = articles;
    }



    public static ResponseEntity<AdminArticleListResponseDto> success(Page<AdminArticleDetailDto> articles) {
        AdminArticleListResponseDto result = new AdminArticleListResponseDto(articles);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }





}

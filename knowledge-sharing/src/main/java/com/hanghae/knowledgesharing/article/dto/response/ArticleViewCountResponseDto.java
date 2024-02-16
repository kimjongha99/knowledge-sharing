package com.hanghae.knowledgesharing.article.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ArticleViewCountResponseDto {

    private String message;

    @Builder
    public ArticleViewCountResponseDto(String message) {
        this.message=message;
    }

}

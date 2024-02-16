package com.hanghae.knowledgesharing.article.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DeleteArticleResponseDto {

    private  String message;

    @Builder
    public DeleteArticleResponseDto(String message) {
        this.message=message;
    }


}

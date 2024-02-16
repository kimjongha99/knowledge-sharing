package com.hanghae.knowledgesharing.article.dto.response;


import lombok.Builder;
import lombok.Getter;

@Getter
public class PatchArticleResponseDto {

    private  String message;

    @Builder
    public PatchArticleResponseDto(String message) {
        this.message=message;
    }


}


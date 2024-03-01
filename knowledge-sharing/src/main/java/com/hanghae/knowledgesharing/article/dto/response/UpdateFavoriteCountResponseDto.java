package com.hanghae.knowledgesharing.article.dto.response;

import lombok.Getter;

@Getter
public class UpdateFavoriteCountResponseDto {

    private  String message;

    public UpdateFavoriteCountResponseDto(String message) {
        this.message = message;
    }
}

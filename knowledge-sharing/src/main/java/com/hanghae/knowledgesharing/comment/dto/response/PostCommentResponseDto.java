package com.hanghae.knowledgesharing.comment.dto.response;


import lombok.Builder;
import lombok.Getter;

@Getter
public class PostCommentResponseDto {

    private String message;

    @Builder
    public PostCommentResponseDto(String message) {
        this.message = message;
    }
}

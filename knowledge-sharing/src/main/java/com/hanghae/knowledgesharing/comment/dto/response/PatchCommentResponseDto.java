package com.hanghae.knowledgesharing.comment.dto.response;


import lombok.Getter;

@Getter
public class PatchCommentResponseDto {
    private String message;

    public PatchCommentResponseDto(String message) {
        this.message = message;
    }
}

package com.hanghae.knowledgesharing.comment.dto.request.comment;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostCommentRequestDto {
    @NotBlank
    private String content;
}

package com.hanghae.knowledgesharing.comment.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentListResponseDto {

       private Long id;
        private String content;
        private Long articleId;
        private String userId;

    public CommentListResponseDto(Long id, String content, Long articleId, String userId) {
        this.id = id;
        this.content = content;
        this.articleId = articleId;
        this.userId = userId;
    }
}

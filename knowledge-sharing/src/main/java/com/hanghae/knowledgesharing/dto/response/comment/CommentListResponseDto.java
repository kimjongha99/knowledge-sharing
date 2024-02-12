package com.hanghae.knowledgesharing.dto.response.comment;

import com.hanghae.knowledgesharing.dto.response.ResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@NoArgsConstructor
public class CommentListResponseDto extends ResponseDto {
    private Page<CommentDetailDto> comments;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CommentDetailDto  extends ResponseDto{
        private Long id;
        private String content;
        private Long articleId;
        private String userId;

        public CommentDetailDto(Long id, String content, Long articleId, String userId) {
            this.id = id;
            this.content = content;
            this.articleId = articleId;
            this.userId = userId;
        }
    }
    public CommentListResponseDto(Page<CommentDetailDto> comments) {
        super();
        this.comments = comments;
    }


    public static ResponseEntity<CommentListResponseDto> success(Page<CommentDetailDto> comments) {
        CommentListResponseDto result = new CommentListResponseDto(comments);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


}
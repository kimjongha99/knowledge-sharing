package com.hanghae.knowledgesharing.article.dto.response.article;


import com.hanghae.knowledgesharing.common.dto.ResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ArticleListResponseDto extends ResponseDto {

    private Page<ArticleDetailDto> articles;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ArticleDetailDto {
    private Long boardId;
    private String title;
    private int favoriteCount;
    private  int viewCount;
    private List<String> hashtags;
    private List<String>  imageUrls;
    private  String writer;

        public ArticleDetailDto(Long boardId, String title, int favoriteCount, int viewCount, List<String> hashtags, List<String> imageUrls, String writer) {
            this.boardId = boardId;
            this.title = title;
            this.favoriteCount = favoriteCount;
            this.viewCount = viewCount;
            this.hashtags = hashtags;
            this.imageUrls = imageUrls;
            this.writer = writer;
        }
    }

    public ArticleListResponseDto(Page<ArticleDetailDto> articles) {
        super();
        this.articles = articles;
    }
    public static ResponseEntity<ArticleListResponseDto> success(Page<ArticleDetailDto> articles) {
        ArticleListResponseDto result = new ArticleListResponseDto(articles);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }



}

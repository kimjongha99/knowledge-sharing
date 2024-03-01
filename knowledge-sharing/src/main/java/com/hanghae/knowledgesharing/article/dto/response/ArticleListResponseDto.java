package com.hanghae.knowledgesharing.article.dto.response;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class ArticleListResponseDto {

    private Page<ArticleDetailDto> articles;

    // ArticleDetailDto 내부 클래스
    @Getter
    public static class ArticleDetailDto {
        private Long boardId;
        private String title;
        private int favoriteCount;
        private int viewCount;
        private List<String> hashtags;
        private List<String> imageUrls;
        private String writer;

        // ArticleDetailDto 생성자
        public ArticleDetailDto(Long boardId, String title, int favoriteCount, int viewCount,
                                List<String> hashtags, List<String> imageUrls, String writer) {
            this.boardId = boardId;
            this.title = title;
            this.favoriteCount = favoriteCount;
            this.viewCount = viewCount;
            this.hashtags = hashtags;
            this.imageUrls = imageUrls;
            this.writer = writer;
        }


    }

    // ArticleListResponseDto 생성자
    public ArticleListResponseDto(Page<ArticleDetailDto> articles) {
        this.articles = articles;
    }
}

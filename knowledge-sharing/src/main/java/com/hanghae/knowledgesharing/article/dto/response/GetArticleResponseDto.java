package com.hanghae.knowledgesharing.article.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Getter
public class GetArticleResponseDto {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private int favoriteCount;
    private int viewCount;
    private List<String> articleHashtags;
    private List<String> imageUrls;



    @Builder
    public GetArticleResponseDto(Long id, String title, String content, String writer, int favoriteCount, int viewCount, List<String> articleHashtags, List<String> imageUrls) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.favoriteCount = favoriteCount;
        this.viewCount = viewCount;
        this.articleHashtags = articleHashtags;
        this.imageUrls = imageUrls;
    }
}

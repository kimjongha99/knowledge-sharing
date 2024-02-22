package com.hanghae.knowledgesharing.article.dto.response;

import com.hanghae.knowledgesharing.common.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Top3ArticleResponseDto {

    private  Long id;
    private String title;
    private String content;

    public static Top3ArticleResponseDto fromArticle(Article article) {
        return new Top3ArticleResponseDto(
                article.getId(),
                article.getTitle(),
                article.getContent());
    }
}

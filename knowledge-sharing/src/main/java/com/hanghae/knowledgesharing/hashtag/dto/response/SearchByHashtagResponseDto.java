package com.hanghae.knowledgesharing.hashtag.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchByHashtagResponseDto {
    private List<ArticleSimpleDto> articles;
    private List<FlashcardSetSimpleDto> flashcardSets;

    // ArticleSimpleDto 정의
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ArticleSimpleDto {
        private Long id;
        private String title;
        // 필요한 필드 및 생성자, getter 추가
    }

    // FlashcardSetSimpleDto 정의
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FlashcardSetSimpleDto {
        private Long id;
        private String title;
        // 필요한 필드 및 생성자, getter 추가
    }
}
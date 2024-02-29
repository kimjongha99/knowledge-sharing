package com.hanghae.knowledgesharing.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserQuizResponseDto {

    private List<FlashcardSetSimpleDto> quiz;
    private int page;
    private int size;
    private  Long totalElements;



    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FlashcardSetSimpleDto {
        private Long id;
        private String title;
        private String content;

    }
}

package com.hanghae.knowledgesharing.cardSet.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DetailFlashCardSetDto {
    private Long flashCardSetId;
    private String title;
    private String description;
    private List<String> hashtags;
    private List<DetailFlashCardDto> flashcards;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailFlashCardDto {
        private String term;
        private String definition;
    }


}

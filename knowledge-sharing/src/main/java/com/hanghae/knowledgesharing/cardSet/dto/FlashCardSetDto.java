package com.hanghae.knowledgesharing.cardSet.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FlashCardSetDto {

    private Long flashCardSetId;
    private String title;
    private String description;
    private List<String> hashtags;
    private List<FlashCardDto> flashcards;

}
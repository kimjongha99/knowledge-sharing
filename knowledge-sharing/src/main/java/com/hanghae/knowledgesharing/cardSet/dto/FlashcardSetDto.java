package com.hanghae.knowledgesharing.cardSet.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FlashcardSetDto {
    private String title;
    private String description;
    private List<String> hashtags;


    private List<FlashcardDto> flashcards;


}
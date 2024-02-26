package com.hanghae.knowledgesharing.cardSet.service;

import com.hanghae.knowledgesharing.cardSet.dto.FlashcardSetDto;

public interface CardSetService {
    String createCardSet(FlashcardSetDto flashcardSetDto, String userId);
}

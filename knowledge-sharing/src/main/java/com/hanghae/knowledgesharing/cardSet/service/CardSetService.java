package com.hanghae.knowledgesharing.cardSet.service;

import com.hanghae.knowledgesharing.cardSet.dto.FlashCardDto;
import com.hanghae.knowledgesharing.cardSet.dto.FlashCardSetDto;
import com.hanghae.knowledgesharing.cardSet.dto.response.GetFlashCardListsResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CardSetService {
    String createCardSet(FlashCardSetDto flashcardSetDto, String userId);


    GetFlashCardListsResponseDto getFlashCardList(Pageable pageable, String title, String description);

    String updateCardSet(Long cardSetId, FlashCardSetDto flashCardSetDto, String userId);

    String deleteCardSet(Long cardSetId, String userId);


    @Transactional(readOnly = true)
    List<FlashCardDto> getFlashCard(Long cardSetId);
}

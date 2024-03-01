package com.hanghae.knowledgesharing.cardSet.dto.response;

import com.hanghae.knowledgesharing.cardSet.dto.FlashCardSetDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

// 페이징 처리된 낱말카드 세트 목록을 응답하는 DTO
@Getter
@Setter
public class GetFlashCardListsResponseDto {
    private List<FlashCardSetDto> flashcardSets;
    private int page;
    private int size;
    private  Long totalElements;
}

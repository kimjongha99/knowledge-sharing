package com.hanghae.knowledgesharing.cardSet.controller;

import com.hanghae.knowledgesharing.cardSet.dto.FlashCardDto;
import com.hanghae.knowledgesharing.cardSet.dto.FlashCardSetDto;
import com.hanghae.knowledgesharing.cardSet.dto.response.DetailFlashCardSetDto;
import com.hanghae.knowledgesharing.cardSet.dto.response.GetFlashCardListsResponseDto;
import com.hanghae.knowledgesharing.cardSet.service.CardSetService;
import com.hanghae.knowledgesharing.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/card-set")
public class CardSetController {

    private  final  CardSetService cardSetService;


    @PostMapping("")
    public ResponseDto<String> createCardSet(
            @RequestBody FlashCardSetDto flashCardSetDto,
            @AuthenticationPrincipal String userId)
    {
        String result = cardSetService.createCardSet(flashCardSetDto,userId);
        return  ResponseDto.success(result);

    }
    /*
     FlashCardSet을 페이징 처리해서 응답한다.
     검색 조건에 제목 , 내용 있다.
     */
    @GetMapping("")
    public ResponseDto<GetFlashCardListsResponseDto> getFlashCardList(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){

        GetFlashCardListsResponseDto response = cardSetService.getFlashCardList(pageable,title,description);
        return ResponseDto.success(response);
    }
    @PutMapping("/{cardSetId}")
    public ResponseDto<String> updateCardSet(
            @PathVariable Long cardSetId,
            @RequestBody FlashCardSetDto flashCardSetDto,
            @AuthenticationPrincipal String userId) {
        String result = cardSetService.updateCardSet(cardSetId, flashCardSetDto, userId);
        return ResponseDto.success(result);
    }


    @DeleteMapping("/{cardSetId}")
    public ResponseDto<String> deleteCardSet(
            @PathVariable Long cardSetId,
            @AuthenticationPrincipal String userId) {
        String result = cardSetService.deleteCardSet(cardSetId, userId);
        return ResponseDto.success(result);
    }

    @GetMapping("/{cardSetId}")
    public  ResponseDto<List< FlashCardDto>>  getFlashCard(
            @PathVariable Long cardSetId){
        List< FlashCardDto> response = cardSetService.getFlashCard(cardSetId);
        return ResponseDto.success(response);

    }

    @GetMapping("/detail/{cardSetId}")
    public ResponseDto<DetailFlashCardSetDto> getDetailFlashCard(
            @PathVariable Long cardSetId
    ){
        DetailFlashCardSetDto response = cardSetService.getDetailFlashCard(cardSetId);
        return ResponseDto.success(response);
    }



}

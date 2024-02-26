package com.hanghae.knowledgesharing.cardSet.controller;

import com.hanghae.knowledgesharing.cardSet.dto.FlashcardSetDto;
import com.hanghae.knowledgesharing.cardSet.service.CardSetService;
import com.hanghae.knowledgesharing.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/card-set")
public class CardSetController {

    private  final  CardSetService cardSetService;


    @PostMapping("")
    public ResponseDto<String> createCardSet(
            @RequestBody FlashcardSetDto flashcardSetDto,
            @AuthenticationPrincipal String userId)
    {
        String result = cardSetService.createCardSet(flashcardSetDto,userId);
        return  ResponseDto.success(result);

    }



}

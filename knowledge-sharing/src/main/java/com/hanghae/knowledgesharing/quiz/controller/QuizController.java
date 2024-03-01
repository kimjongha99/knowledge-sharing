package com.hanghae.knowledgesharing.quiz.controller;

import com.hanghae.knowledgesharing.common.dto.ResponseDto;
import com.hanghae.knowledgesharing.quiz.dto.response.QuizListResponseDto;
import com.hanghae.knowledgesharing.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/quiz")
public class QuizController {


    private final QuizService quizService;


    @GetMapping("{cardSetId}")
    public ResponseDto<List<QuizListResponseDto>> getQuiz(
            @PathVariable Long  cardSetId)
    {
        List<QuizListResponseDto> response = quizService.getQuiz(cardSetId);

        return ResponseDto.success(response);
    }

}

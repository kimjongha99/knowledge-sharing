package com.hanghae.knowledgesharing.quiz.service;

import com.hanghae.knowledgesharing.quiz.dto.response.QuizListResponseDto;

import java.util.List;

public interface QuizService {
    List<QuizListResponseDto> getQuiz(Long  cardSetId);
}

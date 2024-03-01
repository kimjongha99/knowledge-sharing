package com.hanghae.knowledgesharing.quiz.dto.response;


import lombok.Getter;

import java.util.List;

@Getter
public class QuizListResponseDto {

    private String title;
    private String description;
    private List<String> hashtag;

    private List<QuizDetail> quizDetailList;

    public QuizListResponseDto(String title, String description, List<String> hashtag, List<QuizDetail> quizDetailList) {
        this.title = title;
        this.description = description;
        this.hashtag = hashtag;
        this.quizDetailList = quizDetailList;
    }
}

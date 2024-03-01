package com.hanghae.knowledgesharing.quiz.dto.response;


import lombok.Getter;

@Getter
public class QuizDetail {

    private Long realId;
    private String term;
    private String answer;
    private String failureAnswer1;
    private String failureAnswer2;
    private String failureAnswer3;


    public QuizDetail(Long realId, String term, String answer, String failureAnswer1, String failureAnswer2, String failureAnswer3) {
        this.realId = realId;
        this.term = term;
        this.answer = answer;
        this.failureAnswer1 = failureAnswer1;
        this.failureAnswer2 = failureAnswer2;
        this.failureAnswer3 = failureAnswer3;
    }
}

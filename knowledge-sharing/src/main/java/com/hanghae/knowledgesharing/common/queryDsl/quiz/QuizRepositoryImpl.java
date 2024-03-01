package com.hanghae.knowledgesharing.common.queryDsl.quiz;

import com.hanghae.knowledgesharing.common.entity.Flashcard;
import com.hanghae.knowledgesharing.common.entity.FlashcardSet;
import com.hanghae.knowledgesharing.common.entity.QFlashcard;
import com.hanghae.knowledgesharing.common.entity.QFlashcardSet;
import com.hanghae.knowledgesharing.quiz.dto.response.QuizDetail;
import com.hanghae.knowledgesharing.quiz.dto.response.QuizListResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class QuizRepositoryImpl  implements QuizRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;




}

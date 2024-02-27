package com.hanghae.knowledgesharing.quiz.service.Impl;

import com.hanghae.knowledgesharing.cardSet.repository.CardSetRepository;
import com.hanghae.knowledgesharing.common.entity.CardHashTag;
import com.hanghae.knowledgesharing.common.entity.Flashcard;
import com.hanghae.knowledgesharing.common.entity.FlashcardSet;
import com.hanghae.knowledgesharing.common.exception.CustomException;
import com.hanghae.knowledgesharing.common.exception.ErrorCode;
import com.hanghae.knowledgesharing.quiz.dto.response.QuizDetail;
import com.hanghae.knowledgesharing.quiz.dto.response.QuizListResponseDto;
import com.hanghae.knowledgesharing.quiz.repository.CardRepository;
import com.hanghae.knowledgesharing.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl  implements QuizService {


    private final CardRepository cardRepository;
    private final CardSetRepository cardSetRepository;
    @Override
    public List<QuizListResponseDto> getQuiz(Long  cardSetId) {
        FlashcardSet flashcardSet  = cardSetRepository.findById(cardSetId)
                .orElseThrow(() -> new CustomException(ErrorCode.CardSetNotFound));

        List<String> hashtags = flashcardSet .getCardHashTags().stream()
                .map(CardHashTag::getHashtagName) // CardHashTag 엔티티에 getTagName() 메소드가 존재해야 함
                .collect(Collectors.toList());

        // FlashCard도 가져오기
        List<Flashcard> flashcards = cardRepository.findByFlashcardSetId(cardSetId);

        List<String> allDefinitions = flashcards.stream()
                .map(Flashcard::getDefinition)
                .collect(Collectors.toList());

        List<QuizDetail> quizDetails = flashcards.stream().map(flashcard -> {
            List<String> otherDefinitions = new ArrayList<>(allDefinitions);
            otherDefinitions.remove(flashcard.getDefinition());
            Collections.shuffle(otherDefinitions);
            List<String> failureAnswers = otherDefinitions.subList(0, Math.min(3, otherDefinitions.size()));

            return new QuizDetail(
                    flashcard.getTerm(),
                    flashcard.getDefinition(),
                    failureAnswers.size() > 0 ? failureAnswers.get(0) : null,
                    failureAnswers.size() > 1 ? failureAnswers.get(1) : null,
                    failureAnswers.size() > 2 ? failureAnswers.get(2) : null
            );
        }).collect(Collectors.toList());

        QuizListResponseDto quizListResponseDto = new QuizListResponseDto(
                flashcardSet.getTitle(),
                flashcardSet.getDescription(),
                hashtags,
                quizDetails
        );
        return List.of(quizListResponseDto);

        }




    }


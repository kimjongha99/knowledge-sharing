package com.hanghae.knowledgesharing.cardSet.service.impl;


import com.hanghae.knowledgesharing.cardSet.dto.FlashcardDto;
import com.hanghae.knowledgesharing.cardSet.dto.FlashcardSetDto;
import com.hanghae.knowledgesharing.cardSet.repository.CardSetRepository;
import com.hanghae.knowledgesharing.cardSet.service.CardSetService;
import com.hanghae.knowledgesharing.common.entity.*;
import com.hanghae.knowledgesharing.common.enums.HashTagTypeEnum;
import com.hanghae.knowledgesharing.common.exception.CustomException;
import com.hanghae.knowledgesharing.common.exception.ErrorCode;
import com.hanghae.knowledgesharing.hashtag.repository.HashTagRepository;
import com.hanghae.knowledgesharing.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardSetServiceImpl  implements CardSetService {


    private final CardSetRepository cardSetRepository;
    private final UserRepository userRepository;
    private final HashTagRepository hashTagRepository;

    @Override
    public String createCardSet(FlashcardSetDto flashcardSetDto, String userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new CustomException(ErrorCode.UserNotFound);
        }

        // FlashcardSet 객체 생성
        FlashcardSet flashcardSet = new FlashcardSet(
                flashcardSetDto.getTitle(),
                flashcardSetDto.getDescription(),
                HashTagTypeEnum.QUIZ_TAG, // 해시태그 유형 설정
                user // FlashcardSet을 생성하는 사용자 설정
        );

         // 해시태그 처리
        for (String tagName : flashcardSetDto.getHashtags()) {
            HashTag hashTag = hashTagRepository.findByTagName(tagName)
                    .orElseGet(() -> {
                        // 새 HashTag 인스턴스를 만듭니다.
                        HashTag newHashTag = new HashTag(tagName, HashTagTypeEnum.QUIZ_TAG); // HashTag 생성자 수정
                        return hashTagRepository.save(newHashTag); // HashTag를 저장하고 반환합니다.
                    });
            // FlashcardSet과 HashTag를 연결하는 로직
            // 예를 들어, CardHashTag 인스턴스를 만들고 연결하는 과정이 필요합니다.
            // 이 부분은 CardHashTag 엔티티의 구조와 생성자에 따라 달라집니다.
            CardHashTag cardHashTag = new CardHashTag(flashcardSet, hashTag);
            flashcardSet.getCardHashTags().add(cardHashTag); // FlashcardSet에 CardHashTag를 추가합니다.
        }

        // Flashcard  생성
        for (FlashcardDto flashcardDto : flashcardSetDto.getFlashcards()) {
            Flashcard flashcard = new Flashcard(
                    flashcardDto.getTerm(),
                    flashcardDto.getDefinition(),
                    flashcardSet // 현재 생성 중인 FlashcardSet에 Flashcard가 속하도록 설정
            );
            // 생성된 Flashcard 인스턴스를 FlashcardSet의 flashcards 리스트에 추가
            flashcardSet.getFlashcards().add(flashcard);
        }
        // FlashcardSet 저장 (Cascade 설정에 따라 Flashcard와 CardHashTag도 함께 저장됨)
        cardSetRepository.save(flashcardSet);





        return "낱말카드 작성 완료";
    }


}

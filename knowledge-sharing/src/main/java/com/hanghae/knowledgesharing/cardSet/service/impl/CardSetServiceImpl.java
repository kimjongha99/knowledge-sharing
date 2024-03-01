package com.hanghae.knowledgesharing.cardSet.service.impl;


import com.hanghae.knowledgesharing.cardSet.dto.FlashCardDto;
import com.hanghae.knowledgesharing.cardSet.dto.FlashCardSetDto;
import com.hanghae.knowledgesharing.cardSet.dto.response.DetailFlashCardSetDto;
import com.hanghae.knowledgesharing.cardSet.dto.response.GetFlashCardListsResponseDto;
import com.hanghae.knowledgesharing.cardSet.repository.CardSetRepository;
import com.hanghae.knowledgesharing.cardSet.service.CardSetService;
import com.hanghae.knowledgesharing.common.entity.*;
import com.hanghae.knowledgesharing.common.enums.HashTagTypeEnum;
import com.hanghae.knowledgesharing.common.exception.CustomException;
import com.hanghae.knowledgesharing.common.exception.ErrorCode;
import com.hanghae.knowledgesharing.hashtag.repository.HashTagRepository;
import com.hanghae.knowledgesharing.quiz.repository.CardRepository;
import com.hanghae.knowledgesharing.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardSetServiceImpl  implements CardSetService {


    private final CardSetRepository cardSetRepository;
    private final UserRepository userRepository;
    private final HashTagRepository hashTagRepository;
    private final CardRepository cardRepository;
    @Override
    public String createCardSet(FlashCardSetDto flashcardSetDto, String userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new CustomException(ErrorCode.UserNotFound);
        }
        List<FlashCardDto> flashcards = flashcardSetDto.getFlashcards();
        if (flashcards == null || flashcards.isEmpty()) {
            throw new CustomException(ErrorCode.FLASHCARD_EMPTY); // Flashcard가 비어있음
        }
        if (flashcards.size() < 4) {
            throw new CustomException(ErrorCode.FLASHCARD_MINIMUM_NOT_MET); // Flashcard의 최소 개수 미달
        }
        if (flashcards.size() > 30) {
            throw new IllegalArgumentException("FlashCardDto의 개수는 최대 30개까지 가능합니다.");
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
            CardHashTag cardHashTag = new CardHashTag(flashcardSet, hashTag);
            flashcardSet.getCardHashTags().add(cardHashTag); // FlashcardSet에 CardHashTag를 추가합니다.
        }

        // Flashcard  생성
        for (FlashCardDto flashcardDto : flashcardSetDto.getFlashcards()) {
            Flashcard flashcard = new Flashcard(
                    flashcardDto.getTerm(),
                    flashcardDto.getDefinition(),
                    flashcardSet // 현재 생성 중인 FlashcardSet에 Flashcard가 속하도록 설정
            );
            flashcardSet.getFlashcards().add(flashcard);
        }
        cardSetRepository.save(flashcardSet);
        return "낱말카드 작성 완료";
    }

    @Override
    public GetFlashCardListsResponseDto getFlashCardList(Pageable pageable, String title, String description) {

        if (title != null) {
            title = "%" + title + "%";
        }
        if (description != null) {
            description = "%" + description + "%";
        }
        Page<FlashcardSet> flashcardSetPage = cardSetRepository.getFlashcardSets(title, description, pageable);


        List<FlashCardSetDto> flashCardSetDtoList = flashcardSetPage.getContent().stream()
                .map(flashcardSet -> {
                    FlashCardSetDto dto = new FlashCardSetDto();
                    dto.setFlashCardSetId(flashcardSet.getId());
                    dto.setTitle(flashcardSet.getTitle());
                    dto.setDescription(flashcardSet.getDescription());
                    List<String> hashtags = flashcardSet.getCardHashTags().stream()
                            .map(CardHashTag::getHashtagName) // CardHashTag 엔티티에 getTagName() 메소드가 존재해야 함
                            .collect(Collectors.toList());
                    dto.setHashtags(hashtags);
                    return dto;

                })
                .collect(Collectors.toList());

        GetFlashCardListsResponseDto response = new GetFlashCardListsResponseDto();
        response.setFlashcardSets(flashCardSetDtoList);
        response.setPage(flashcardSetPage.getNumber());
        response.setSize(flashcardSetPage.getSize());
        response.setTotalElements(flashcardSetPage.getTotalElements());

        return response;

    }

    @Override
    public String updateCardSet(Long cardSetId, FlashCardSetDto flashCardSetDto, String userId) {
// 사용자 확인
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new CustomException(ErrorCode.UserNotFound);
        }
        // FlashcardSet 찾기
        FlashcardSet flashcardSet = cardSetRepository.findById(cardSetId)
                .orElseThrow(() -> new CustomException(ErrorCode.CardSetNotFound));

        // 권한 검증: 카드 세트의 소유자인지 확인
        if (!flashcardSet.getUser().equals(user)) {
            throw new CustomException(ErrorCode.NoPermission);
        }

        // FlashcardSet 정보 업데이트
        flashcardSet.setTitle(flashCardSetDto.getTitle());
        flashcardSet.setDescription(flashCardSetDto.getDescription());

        flashcardSet.getCardHashTags().clear();
        // 새로운 해시태그 처리 및 추가
        for (String tagName : flashCardSetDto.getHashtags()) {
            HashTag hashTag = hashTagRepository.findByTagName(tagName)
                    .orElseGet(() -> new HashTag(tagName, HashTagTypeEnum.QUIZ_TAG));
            CardHashTag cardHashTag = new CardHashTag(flashcardSet, hashTag);
            flashcardSet.getCardHashTags().add(cardHashTag);
        }
// 기존 Flashcards 삭제
        flashcardSet.getFlashcards().clear();

        // 새 Flashcard 생성 및 추가
        for (FlashCardDto flashCardDto : flashCardSetDto.getFlashcards()) {
            Flashcard flashcard = new Flashcard(
                    flashCardDto.getTerm(),
                    flashCardDto.getDefinition(),
                    flashcardSet
            );
            flashcardSet.getFlashcards().add(flashcard);
        }
        // FlashcardSet 저장
        cardSetRepository.save(flashcardSet);

        return "플래시카드 세트가 성공적으로 업데이트되었습니다.";


    }

    @Override
    public String deleteCardSet(Long cardSetId, String userId) {
        FlashcardSet flashcardSet = cardSetRepository.findById(cardSetId)
                .orElseThrow(() -> new CustomException(ErrorCode.CardSetNotFound));
        if (!userRepository.existsByUserId(userId)) {
            throw new CustomException(ErrorCode.UserNotFound);
        }
        if (!flashcardSet.getUser().getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.PermissionDenied);
        }
        cardSetRepository.delete(flashcardSet);
        return "플래시카드 세트가 삭제되었습니다.";

    }

    @Override
    @Transactional(readOnly = true)
    public List<FlashCardDto> getFlashCard(Long cardSetId) {
        List<Flashcard> flashcards = cardRepository.findByFlashcardSetId(cardSetId);
        AtomicInteger index = new AtomicInteger(1); // Use AtomicInteger to increment within the lambda

        return flashcards.stream().map(flashcard -> {
            FlashCardDto dto = new FlashCardDto();
            dto.setFlashCardId(flashcard.getId());
            dto.setRealId(String.valueOf(index.getAndIncrement())); // Increment index for each mapped dto

            dto.setTerm(flashcard.getTerm());
            dto.setDefinition(flashcard.getDefinition());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public DetailFlashCardSetDto getDetailFlashCard(Long cardSetId) {
        FlashcardSet flashcardSet = cardSetRepository.findById(cardSetId)
                .orElseThrow(() -> new CustomException(ErrorCode.CardSetNotFound));


        // FlashcardSet에서 Flashcard 목록을 추출하여 DetailFlashCardDto 목록으로 변환
        List<DetailFlashCardSetDto.DetailFlashCardDto> flashCardDtos = flashcardSet.getFlashcards().stream()
                .map(flashcard -> new DetailFlashCardSetDto.DetailFlashCardDto(flashcard.getTerm(), flashcard.getDefinition()))
                .collect(Collectors.toList());

        // FlashcardSet에서 해시태그 목록 추출 (가정: CardHashTag 엔티티에서 해시태그 문자열을 추출하는 getHashtag 메소드가 존재한다고 가정)
        List<String> hashtags = flashcardSet.getCardHashTags().stream()
                .map(CardHashTag::getHashtagName) // Assuming Hashtag entity has getName()
                .collect(Collectors.toList());

        // DetailFlashCardSetDto 객체 생성 및 반환
        return new DetailFlashCardSetDto(
                flashcardSet.getId(),
                flashcardSet.getTitle(),
                flashcardSet.getDescription(),
                hashtags,
                flashCardDtos
        );    }

}
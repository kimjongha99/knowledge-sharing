package com.hanghae.knowledgesharing.hashtag.sevice.Impl;

import com.hanghae.knowledgesharing.article.repository.ArticleRepository;
import com.hanghae.knowledgesharing.cardSet.repository.CardSetRepository;
import com.hanghae.knowledgesharing.common.entity.Article;
import com.hanghae.knowledgesharing.common.entity.FlashcardSet;
import com.hanghae.knowledgesharing.hashtag.dto.response.SearchByHashtagResponseDto;
import com.hanghae.knowledgesharing.hashtag.sevice.HashTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class HashTagServiceImpl implements HashTagService {

    private final ArticleRepository articleRepository;
    private final CardSetRepository flashcardSetRepository;


    @Override
    @Transactional(readOnly = true)
    public SearchByHashtagResponseDto searchByHashtag(String tagName) {
        List<Article> articles = articleRepository.findByHashtagsTagName(tagName);
        List<FlashcardSet> flashcardSets = flashcardSetRepository.findByHashtagsTagName(tagName);

        List<SearchByHashtagResponseDto.ArticleSimpleDto> articleDtos = articles.stream()
                .map(article -> new SearchByHashtagResponseDto.ArticleSimpleDto(article.getId(), article.getTitle()))
                .collect(Collectors.toList());


        List<SearchByHashtagResponseDto.FlashcardSetSimpleDto> flashcardSetDtos = flashcardSets.stream()
                .map(flashcardSet -> new SearchByHashtagResponseDto.FlashcardSetSimpleDto(flashcardSet.getId(), flashcardSet.getTitle()))
                .collect(Collectors.toList());

        return new SearchByHashtagResponseDto(articleDtos, flashcardSetDtos);
    }




}

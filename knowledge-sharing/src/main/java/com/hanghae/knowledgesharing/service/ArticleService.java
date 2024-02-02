package com.hanghae.knowledgesharing.service;

import com.hanghae.knowledgesharing.dto.request.article.PatchArticleRequestDto;
import com.hanghae.knowledgesharing.dto.request.article.PostArticleRequestDto;
import com.hanghae.knowledgesharing.dto.response.article.DeleteArticleResponseDto;
import com.hanghae.knowledgesharing.dto.response.article.GetArticleResponseDto;
import com.hanghae.knowledgesharing.dto.response.article.PatchArticleResponseDto;
import com.hanghae.knowledgesharing.dto.response.article.PostArticleResponseDto;
import org.springframework.http.ResponseEntity;

public interface ArticleService {

    ResponseEntity<? super PostArticleResponseDto> postArticle(PostArticleRequestDto requestDto, String userId);
    ResponseEntity<? super GetArticleResponseDto> getArticle(Long ArticleId);

    ResponseEntity<? super PatchArticleResponseDto> patchArticle(PatchArticleRequestDto requestDto, Long articleId, String userId);

    ResponseEntity<? super DeleteArticleResponseDto> deleteArticle(Long articleId  , String userId);
}

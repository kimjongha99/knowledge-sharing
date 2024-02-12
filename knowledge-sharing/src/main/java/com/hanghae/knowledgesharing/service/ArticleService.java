package com.hanghae.knowledgesharing.service;

import com.hanghae.knowledgesharing.dto.request.article.PatchArticleRequestDto;
import com.hanghae.knowledgesharing.dto.request.article.PostArticleRequestDto;
import com.hanghae.knowledgesharing.dto.request.article.UpdateFavoriteCountRequestDto;
import com.hanghae.knowledgesharing.dto.response.article.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ArticleService {

    ResponseEntity<? super PostArticleResponseDto> postArticle(PostArticleRequestDto requestDto, String userId);
    ResponseEntity<? super GetArticleResponseDto> getArticle(Long ArticleId);

    ResponseEntity<? super PatchArticleResponseDto> patchArticle(PatchArticleRequestDto requestDto, Long articleId, String userId);

    ResponseEntity<? super DeleteArticleResponseDto> deleteArticle(Long articleId  , String userId);

    ResponseEntity<? super ArticleListResponseDto> getArticleList(Pageable pageable);

    //조회수 증가
    ResponseEntity<ArticleViewCountResponseDto> incrementArticleViewCount(String userId, Long articleId);


    ResponseEntity<UpdateFavoriteCountResponseDto>  updateFavoriteCount(Long articleId, UpdateFavoriteCountRequestDto requestDto);
}

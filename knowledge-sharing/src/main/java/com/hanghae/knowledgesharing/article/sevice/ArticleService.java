package com.hanghae.knowledgesharing.article.sevice;

import com.hanghae.knowledgesharing.article.dto.request.PatchArticleRequestDto;
import com.hanghae.knowledgesharing.article.dto.request.PostArticleRequestDto;
import com.hanghae.knowledgesharing.article.dto.request.UpdateFavoriteCountRequestDto;
import com.hanghae.knowledgesharing.article.dto.response.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleService {
    String postArticle(PostArticleRequestDto requestBody, String userId);

    GetArticleResponseDto getArticle(Long articleId);

    PatchArticleResponseDto patchArticle(PatchArticleRequestDto requestDto, Long articleId, String userId);

    DeleteArticleResponseDto deleteArticle(Long articleId, String userId);

    Page<ArticleListResponseDto.ArticleDetailDto> getArticleList(Pageable pageable);

    ArticleViewCountResponseDto incrementArticleViewCount(String userId, Long articleId);

    UpdateFavoriteCountResponseDto updateFavoriteCount(Long articleId, UpdateFavoriteCountRequestDto requestDto, String userId);
}

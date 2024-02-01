package com.hanghae.knowledgesharing.service;

import com.hanghae.knowledgesharing.dto.request.article.PostArticleRequestDto;
import com.hanghae.knowledgesharing.dto.response.article.PostArticleResponseDto;
import org.springframework.http.ResponseEntity;

public interface ArticleService {

    ResponseEntity<? super PostArticleResponseDto> postArticle(PostArticleRequestDto requestDto, String userId);

}

package com.hanghae.knowledgesharing.controller;


import com.hanghae.knowledgesharing.dto.request.article.PatchArticleRequestDto;
import com.hanghae.knowledgesharing.dto.request.article.PostArticleRequestDto;
import com.hanghae.knowledgesharing.dto.response.article.*;
import com.hanghae.knowledgesharing.service.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("")
    public ResponseEntity<? super PostArticleResponseDto> postArticle(@RequestBody PostArticleRequestDto requestBody,
                                                                      @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<? super PostArticleResponseDto> response = articleService.postArticle(requestBody, userId);
        return response;
    }


    @GetMapping("/{articleId}")
    public ResponseEntity<? super GetArticleResponseDto> getArticle(
            @PathVariable("articleId") Long articleId
    ) {
        ResponseEntity<? super GetArticleResponseDto> response = articleService.getArticle(articleId);

        return response;
    }

    @PatchMapping("/{articleId}")
    public ResponseEntity<? super PatchArticleResponseDto> patchArticle(
            @PathVariable("articleId") Long articleId,
            @RequestBody @Valid PatchArticleRequestDto requestDto,
            @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<? super PatchArticleResponseDto> response = articleService.patchArticle(requestDto, articleId, userId);
        return response;
    }


    @DeleteMapping("/{articleId}")
    public ResponseEntity<? super DeleteArticleResponseDto> deleteArticle(
            @PathVariable("articleId") Long articleId,
            @AuthenticationPrincipal String email
    ) {
        ResponseEntity<? super DeleteArticleResponseDto> response = articleService.deleteArticle(articleId, email);
        return response;
    }

    @GetMapping("")
    public ResponseEntity<? super ArticleListResponseDto> getArticleList(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return articleService.getArticleList(pageable);
    }


    @GetMapping("/{articleId}/increase-view")
    public  ResponseEntity<ArticleViewCountResponseDto>  increaseViewCount(
            @PathVariable("articleId") Long articleId,
            @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<ArticleViewCountResponseDto> response = articleService.incrementArticleViewCount( userId, articleId);
        return  response;
    }


}
package com.hanghae.knowledgesharing.article.controller;


import com.hanghae.knowledgesharing.article.dto.request.PatchArticleRequestDto;
import com.hanghae.knowledgesharing.article.dto.request.PostArticleRequestDto;
import com.hanghae.knowledgesharing.article.dto.request.UpdateFavoriteCountRequestDto;
import com.hanghae.knowledgesharing.article.dto.response.*;
import com.hanghae.knowledgesharing.article.sevice.ArticleService;
import com.hanghae.knowledgesharing.common.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
public class ArticleController {

    private final ArticleService articleService;


    @PostMapping("")
    public ResponseDto<String>postArticle(@RequestBody PostArticleRequestDto requestBody,
                                          @AuthenticationPrincipal String userId
    ) {
        String response = articleService.postArticle(requestBody, userId);
        return ResponseDto.success(response);
    }

    @GetMapping("/{articleId}")
    public ResponseDto<GetArticleResponseDto> getArticle(@PathVariable("articleId") Long articleId) {
        GetArticleResponseDto responseDto = articleService.getArticle(articleId);
        return ResponseDto.success(responseDto); // 성공 응답을 반환합니다.
    }


    @PatchMapping("/{articleId}")
    public ResponseDto<PatchArticleResponseDto> patchArticle(
            @PathVariable("articleId") Long articleId,
            @RequestBody @Valid PatchArticleRequestDto requestDto,
            @AuthenticationPrincipal String userId) {
        PatchArticleResponseDto responseDto = articleService.patchArticle(requestDto, articleId, userId);
        return ResponseDto.success(responseDto); // 수정 성공 메시지 포함
    }

    @DeleteMapping("/{articleId}")
    public ResponseDto<DeleteArticleResponseDto> deleteArticle(
            @PathVariable("articleId") Long articleId,
            @AuthenticationPrincipal String userId) {
        DeleteArticleResponseDto responseDto = articleService.deleteArticle(articleId, userId);
        return ResponseDto.success(responseDto);
    }


    @GetMapping("")
    public ResponseDto<Page<ArticleListResponseDto.ArticleDetailDto>> getArticleList(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ArticleListResponseDto.ArticleDetailDto> articles = articleService.getArticleList(pageable);
        return ResponseDto.success(articles);
    }

    @GetMapping("/{articleId}/increase-view")
    public ResponseDto<ArticleViewCountResponseDto> increaseViewCount(
            @PathVariable("articleId") Long articleId,
            @AuthenticationPrincipal String userId) {
        ArticleViewCountResponseDto responseDto = articleService.incrementArticleViewCount(userId, articleId);
        return ResponseDto.success(responseDto);
    }


    @PutMapping("/{articleId}/favorite")
    public ResponseDto<UpdateFavoriteCountResponseDto> updateFavoriteCount(
            @PathVariable("articleId") Long articleId,
            @RequestBody UpdateFavoriteCountRequestDto requestDto,
            @AuthenticationPrincipal String userId) {
        UpdateFavoriteCountResponseDto responseDto = articleService.updateFavoriteCount(articleId, requestDto,userId);
        return ResponseDto.success(responseDto);
    }
}

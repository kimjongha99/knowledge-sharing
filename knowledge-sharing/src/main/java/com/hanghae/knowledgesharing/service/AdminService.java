package com.hanghae.knowledgesharing.service;

import com.hanghae.knowledgesharing.dto.request.admin.UserAdminDeleteRequestDto;
import com.hanghae.knowledgesharing.dto.request.admin.UserRoleChangeRequestDto;
import com.hanghae.knowledgesharing.dto.response.admin.ArticleListResponseDto;
import com.hanghae.knowledgesharing.dto.response.admin.UserAdminDeleteResponseDto;
import com.hanghae.knowledgesharing.dto.response.admin.UserListResponseDto;
import com.hanghae.knowledgesharing.dto.response.admin.UserRoleChangeResponseDto;
import com.hanghae.knowledgesharing.dto.response.article.DeleteArticleResponseDto;
import com.hanghae.knowledgesharing.dto.response.article.GetArticleResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface AdminService {

    ResponseEntity<UserListResponseDto> getAllUsers(String userId, String email, Pageable pageable);

    ResponseEntity<UserRoleChangeResponseDto> userRoleChanges(UserRoleChangeRequestDto requestDto);

    ResponseEntity<UserAdminDeleteResponseDto> userAdminDelete(UserAdminDeleteRequestDto requestDto);

    ResponseEntity<ArticleListResponseDto> getAllArticles(String title, String content, String hashtag, Pageable pageable);

    ResponseEntity<? super DeleteArticleResponseDto> deleteArticle(Long articleId);

    ResponseEntity<? super GetArticleResponseDto> getArticle(Long articleId);
}

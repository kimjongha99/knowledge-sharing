package com.hanghae.knowledgesharing.admin.service;

import com.hanghae.knowledgesharing.admin.dto.request.admin.UserAdminDeleteRequestDto;
import com.hanghae.knowledgesharing.admin.dto.request.admin.UserRoleChangeRequestDto;
import com.hanghae.knowledgesharing.admin.dto.response.admin.AdminArticleListResponseDto;
import com.hanghae.knowledgesharing.admin.dto.response.admin.UserAdminDeleteResponseDto;
import com.hanghae.knowledgesharing.admin.dto.response.admin.UserListResponseDto;
import com.hanghae.knowledgesharing.admin.dto.response.admin.UserRoleChangeResponseDto;
import com.hanghae.knowledgesharing.article.dto.response.article.DeleteArticleResponseDto;
import com.hanghae.knowledgesharing.article.dto.response.article.GetArticleResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface AdminService {

    ResponseEntity<UserListResponseDto> getAllUsers(String userId, String email, Pageable pageable);

    ResponseEntity<UserRoleChangeResponseDto> userRoleChanges(UserRoleChangeRequestDto requestDto);

    ResponseEntity<UserAdminDeleteResponseDto> userAdminDelete(UserAdminDeleteRequestDto requestDto);

    ResponseEntity<AdminArticleListResponseDto> getAllArticles(String title, String content, String hashtag, Pageable pageable);

    ResponseEntity<? super DeleteArticleResponseDto> deleteArticle(Long articleId);

    ResponseEntity<? super GetArticleResponseDto> getArticle(Long articleId);
}

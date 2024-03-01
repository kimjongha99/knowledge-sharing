package com.hanghae.knowledgesharing.admin.sevice;

import com.hanghae.knowledgesharing.admin.dto.request.UserAdminDeleteRequestDto;
import com.hanghae.knowledgesharing.admin.dto.request.UserRoleChangeRequestDto;
import com.hanghae.knowledgesharing.admin.dto.response.*;
import com.hanghae.knowledgesharing.article.dto.response.DeleteArticleResponseDto;
import com.hanghae.knowledgesharing.article.dto.response.GetArticleResponseDto;
import org.springframework.data.domain.Pageable;

public interface AdminService  {
    UserListResponseDto getAllUsers(String userId, String email, Pageable pageable);

    AdminArticleListResponseDto getAllArticles(String title, String content, String hashtag, Pageable pageable);

    UserRoleChangeResponseDto userRoleChanges(UserRoleChangeRequestDto requestDto);

    UserAdminDeleteResponseDto userAdminDelete(UserAdminDeleteRequestDto requestDto);

    DeleteArticleResponseDto deleteArticle(Long articleId);

    GetArticleResponseDto getArticle(Long articleId);

    UserTypeListResponseDto getUsersByLoginType(String loginType, Pageable pageable);
}

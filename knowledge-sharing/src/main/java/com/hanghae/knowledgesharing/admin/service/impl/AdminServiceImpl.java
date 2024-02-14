package com.hanghae.knowledgesharing.admin.service.impl;

import com.hanghae.knowledgesharing.admin.dto.request.admin.UserAdminDeleteRequestDto;
import com.hanghae.knowledgesharing.admin.dto.request.admin.UserRoleChangeRequestDto;
import com.hanghae.knowledgesharing.admin.dto.response.admin.*;
import com.hanghae.knowledgesharing.admin.service.AdminService;
import com.hanghae.knowledgesharing.article.dto.response.article.DeleteArticleResponseDto;
import com.hanghae.knowledgesharing.article.dto.response.article.GetArticleResponseDto;
import com.hanghae.knowledgesharing.article.repository.ArticleRepository;
import com.hanghae.knowledgesharing.common.dto.ResponseDto;
import com.hanghae.knowledgesharing.common.entity.Article;
import com.hanghae.knowledgesharing.common.entity.ArticleHashtag;
import com.hanghae.knowledgesharing.common.entity.Image;
import com.hanghae.knowledgesharing.common.entity.User;
import com.hanghae.knowledgesharing.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    @Override
    public ResponseEntity<UserListResponseDto> getAllUsers(String userId, String email, Pageable pageable) {

        Page<User> usersPage;
        if (userId != null && !userId.isEmpty()) {
            usersPage = userRepository.findByUserIdContaining(userId, pageable);
        } else if (email != null && !email.isEmpty()) {
            usersPage = userRepository.findByEmailContaining(email, pageable);
        } else {
            usersPage = userRepository.findAll(pageable);
        }
        Page<UserDetailDto> userDetailPage = usersPage.map(user -> new UserDetailDto(
                user.getUserId(),
                user.getEmail(),
                user.getRole().getAuthority(),
                user.getType()
        ));

        return UserListResponseDto.success(userDetailPage);

    }

    @Override
    public ResponseEntity<UserRoleChangeResponseDto> userRoleChanges(UserRoleChangeRequestDto requestDto) {
        try {
            int updatedRows = userRepository.updateUserRole(requestDto.getChangeUserId(), requestDto.getRoleEnum());
            if (updatedRows == 0) {
                // 업데이트된 행이 없습니다. 이는 사용자를 찾을 수 없음을 나타냅니다.
                return UserRoleChangeResponseDto.noExistUser();
            }
            return UserRoleChangeResponseDto.success();
        } catch (Exception e) {
            e.printStackTrace();
            return UserRoleChangeResponseDto.databaseError();
        }
    }

    @Override
    public ResponseEntity<UserAdminDeleteResponseDto> userAdminDelete(UserAdminDeleteRequestDto requestDto) {
        try {
            userRepository.deleteById(requestDto.getDeleteUserId());
            return UserAdminDeleteResponseDto.success();
        } catch (Exception e) {
            e.printStackTrace();
            return UserAdminDeleteResponseDto.databaseError();
        }

    }

    @Override
    public ResponseEntity<AdminArticleListResponseDto> getAllArticles(String title, String content, String hashtag, Pageable pageable) {
        Page<Article> articles = articleRepository.findByTitleAndContentAndHashtagName(title, content, hashtag, pageable);

        Page<AdminArticleDetailDto> articleDetailPage = articles.map(article -> {
            // Article의 ArticleHashtag 목록에서 해시태그 이름만 추출하여 List<String>으로 변환
            List<String> hashtags = article.getArticleHashtags().stream()
                    .map(ArticleHashtag::getHashtagName)
                    .collect(Collectors.toList());

            return new AdminArticleDetailDto(
                    article.getId(),
                    article.getTitle(),
                    article.getUser().getUserId(),
                    article.getContent(),
                    article.getTagType(),
                    article.getFavoriteCount(),
                    article.getViewCount(),
                    hashtags // 여기에 변환된 해시태그 리스트를 넘깁니다.
            );
        });


        return AdminArticleListResponseDto.success(articleDetailPage);
    }

    @Override
    public ResponseEntity<? super DeleteArticleResponseDto> deleteArticle(Long articleId) {
        try {
            Article article = articleRepository.findById(articleId)
                    .orElseThrow(() -> new EntityNotFoundException("Article not found with id: " + articleId));
            // Delete the article
            articleRepository.delete(article);
            return DeleteArticleResponseDto.success();
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return DeleteArticleResponseDto.noExistArticle();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }


    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<? super GetArticleResponseDto> getArticle(Long ArticleId) {

        Long id = null;
        String title = null;
        String content = null;
        String writer = null;
        int favoriteCount = 0;
        int viewCount = 0;
        List<String> articleHashtags = null;
        List<String> imageUrls = null;

        try {

            Article article = articleRepository.findById(ArticleId)
                    .orElseThrow(() -> new EntityNotFoundException("Article not found with id: " + ArticleId));

            id = article.getId();
            title = article.getTitle();
            content = article.getContent();
            writer = article.getUser().getUserId();
            favoriteCount = article.getFavoriteCount();
            viewCount = article.getViewCount();

            // 게시물의 해시태그와 이미지 URL 리스트를 추출합니다.
            articleHashtags = article.getArticleHashtags()
                    .stream()
                    .map(ArticleHashtag::getHashtagName) // 가정: ArticleHashtag 엔티티에 getHashtagName 메소드가 있다.
                    .collect(Collectors.toList());

            imageUrls = article.getImages()
                    .stream()
                    .map(Image::getImageUrl) // 가정: Image 엔티티에 getImageUrl 메소드가 있다.
                    .collect(Collectors.toList());


            return GetArticleResponseDto.success(id, title, content, writer, favoriteCount, viewCount, articleHashtags, imageUrls);


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();

        }
    }

}
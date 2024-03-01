package com.hanghae.knowledgesharing.admin.sevice.Impl;

import com.hanghae.knowledgesharing.admin.dto.request.UserAdminDeleteRequestDto;
import com.hanghae.knowledgesharing.admin.dto.request.UserRoleChangeRequestDto;
import com.hanghae.knowledgesharing.admin.dto.response.*;
import com.hanghae.knowledgesharing.admin.sevice.AdminService;
import com.hanghae.knowledgesharing.article.dto.response.DeleteArticleResponseDto;
import com.hanghae.knowledgesharing.article.dto.response.GetArticleResponseDto;
import com.hanghae.knowledgesharing.article.repository.ArticleRepository;
import com.hanghae.knowledgesharing.common.entity.Article;
import com.hanghae.knowledgesharing.common.entity.ArticleHashtag;
import com.hanghae.knowledgesharing.common.entity.Image;
import com.hanghae.knowledgesharing.common.entity.User;
import com.hanghae.knowledgesharing.common.exception.CustomException;
import com.hanghae.knowledgesharing.common.exception.ErrorCode;
import com.hanghae.knowledgesharing.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public UserListResponseDto getAllUsers(String userId, String email, Pageable pageable) {
        Page<User> usersPage;
        if (userId != null && !userId.isEmpty()) {
            usersPage = userRepository.findByUserIdContaining(userId, pageable);
        } else if (email != null && !email.isEmpty()) {
            usersPage = userRepository.findByEmailContaining(email, pageable);
        } else {
            usersPage = userRepository.findAll(pageable);
        }

        Page<UserDetail> userDetailPage = usersPage.map(user -> new UserDetail(
                user.getUserId(),
                user.getEmail(),
                user.getRole().getAuthority(),
                user.getType()
        ));

        UserListResponseDto userListResponseDto = new UserListResponseDto(userDetailPage);
        return userListResponseDto;
    }

    @Override
    public AdminArticleListResponseDto getAllArticles(String title, String content, String hashtag, Pageable pageable) {
        Page<Article> articles = articleRepository.findByTitleAndContentAndHashtagName(title, content, hashtag, pageable);
        Page<AdminArticleDetail> articleDetailPage = articles.map(article -> {
            // Article의 ArticleHashtag 목록에서 해시태그 이름만 추출하여 List<String>으로 변환
            List<String> hashtags = article.getArticleHashtags().stream()
                    .map(ArticleHashtag::getHashtagName)
                    .collect(Collectors.toList());


            AdminArticleDetail adminArticleDetail = new AdminArticleDetail(
                    article.getId(),
                    article.getTitle(),
                    article.getUser().getUserId(),
                    article.getContent(),
                    article.getTagType(),
                    article.getFavoriteCount(),
                    article.getViewCount(),
                    hashtags
            );
            return adminArticleDetail;
        });
        AdminArticleListResponseDto articleListResponseDto = new AdminArticleListResponseDto(articleDetailPage);
        return articleListResponseDto;

    }

    @Override
    public UserRoleChangeResponseDto userRoleChanges(UserRoleChangeRequestDto requestDto) {
        User user = userRepository.findByUserId(requestDto.getChangeUserId());
        if (user == null) {
            throw new CustomException(ErrorCode.UserNotFound);
        }
        // user엔티티에 role을 set해주는 메서드 생성하고
        user.setRole(requestDto.getRoleEnum());
        // 저장
        userRepository.save(user);

        UserRoleChangeResponseDto responseDto = new UserRoleChangeResponseDto("권한수정 성공");
        return responseDto;

    }

    @Override
    public UserAdminDeleteResponseDto userAdminDelete(UserAdminDeleteRequestDto requestDto) {
        userRepository.deleteById(requestDto.getDeleteUserId());
        UserAdminDeleteResponseDto responseDto = new UserAdminDeleteResponseDto("유저 삭제요청 성공");
        return responseDto;
    }

    @Override
    public DeleteArticleResponseDto deleteArticle(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException("Article not found with id: " + articleId));
        // Delete the article
        articleRepository.delete(article);
        DeleteArticleResponseDto responseDto = new DeleteArticleResponseDto("삭제요청 성공");
        return  responseDto;

    }

    @Override
    public GetArticleResponseDto getArticle(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new CustomException(ErrorCode.ArticleNotFound));



        List<String> articleHashtags = article.getArticleHashtags().stream()
                .map(ArticleHashtag::getHashtagName) // Assuming Hashtag entity has getName()
                .collect(Collectors.toList());

        List<String> imageUrls = article.getImages().stream()
                .map(Image::getImageUrl) // Assuming Image entity has getUrl()
                .collect(Collectors.toList());



        // Article 정보를 GetArticleResponseDto로 변환
        GetArticleResponseDto responseDto = GetArticleResponseDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .writer(article.getUser().getUserId()) // Adjust based on your User entity structure
                .favoriteCount(article.getFavoriteCount())
                .viewCount(article.getViewCount())
                .articleHashtags(articleHashtags) // Directly pass the list of hashtags
                .imageUrls(imageUrls) // Directly pass the list of image URLs
                .build();
        return responseDto;

    }



    @Override
    @Transactional(readOnly = true)
    public UserTypeListResponseDto getUsersByLoginType(String loginType, Pageable pageable) {

        Page<User> usersPage = userRepository.findByType(loginType, pageable);


            // 각 사용자 엔터티를 UserTypeInfoDto로 변환합니다.

        List<UserInfo> usersInfo = usersPage.getContent().stream()
                .map(user -> new UserInfo(user.getUserId(), user.getEmail(), user.getType(), user.getProfileImageUrl(), user.getRole()))
                .collect(Collectors.toList());

        return new UserTypeListResponseDto(usersInfo, pageable.getPageNumber(), pageable.getPageSize(), usersPage.getTotalElements());

    }


}
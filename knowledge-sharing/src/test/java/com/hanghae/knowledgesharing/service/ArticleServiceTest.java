//package com.hanghae.knowledgesharing.service;
//
//import com.hanghae.knowledgesharing.article.dto.request.article.PostArticleRequestDto;
//import com.hanghae.knowledgesharing.common.entity.Article;
//import com.hanghae.knowledgesharing.common.entity.User;
//import com.hanghae.knowledgesharing.article.repository.ArticleRepository;
//import com.hanghae.knowledgesharing.hashtag.repository.HashTagRepository;
//import com.hanghae.knowledgesharing.user.repository.UserRepository;
//import com.hanghae.knowledgesharing.article.service.impl.ArticleServiceImpl;
//import com.hanghae.knowledgesharing.user.service.impl.UserServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.ArrayList;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.mockito.MockitoAnnotations.openMocks;
//
//
//@DisplayName("아티클 서비스 테스트")
//class ArticleServiceTest {
//
//    @Mock
//    private ArticleRepository articleRepository;
//
//    @Mock
//    private HashTagRepository hashTagRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private ArticleServiceImpl articleService;
//    @InjectMocks
//    private UserServiceImpl userService;
//
//    @BeforeEach
//    void setUp() {
//        openMocks(this);
//    }
//
//
//    @Test
//    @DisplayName("성공 - 해시태그와 이미지가 포함된 기사 게시")
//    void postArticle_Success() {
//        String userId = "test_user_id";
//        String email = "user@example.com";
//        String type = "app";
//        String profileImageUrl = "image_url";
//        User user = new User(userId, email, type, profileImageUrl);
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//        // PostArticleRequestDto 객체를 생성하고, 필요한 데이터를 설정합니다.
//        PostArticleRequestDto requestDto = new PostArticleRequestDto();
//        requestDto.setTitle("Test Title");
//        requestDto.setContent("Test Content");
//        // 해시태그와 이미지 URL을 설정합니다. 이 예에서는 단순화를 위해 빈 리스트를 사용합니다.
//        requestDto.setHashtags(new ArrayList<>());
//        requestDto.setImageUrls(new ArrayList<>());
//
//        // articleService.postArticle 메서드를 호출합니다.
//        ResponseEntity<?> response = articleService.postArticle(requestDto, userId);
//
//        // 결과가 HttpStatus.OK 인지 확인합니다.
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        // articleRepository.save 가 호출되었는지 확인합니다.
//        verify(articleRepository).save(any(Article.class));
//
//    }
//
//
//    }
package com.hanghae.knowledgesharing.article.sevice;


import com.hanghae.knowledgesharing.article.dto.request.PatchArticleRequestDto;
import com.hanghae.knowledgesharing.article.dto.request.PostArticleRequestDto;
import com.hanghae.knowledgesharing.article.dto.response.GetArticleResponseDto;
import com.hanghae.knowledgesharing.article.repository.ArticleRepository;
import com.hanghae.knowledgesharing.article.sevice.Impl.ArticleServiceImpl;
import com.hanghae.knowledgesharing.auth.dto.request.auth.SignUpRequestDto;
import com.hanghae.knowledgesharing.common.entity.Article;
import com.hanghae.knowledgesharing.common.entity.HashTag;
import com.hanghae.knowledgesharing.common.entity.Image;
import com.hanghae.knowledgesharing.common.entity.User;
import com.hanghae.knowledgesharing.common.exception.CustomException;
import com.hanghae.knowledgesharing.common.exception.ErrorCode;
import com.hanghae.knowledgesharing.hashtag.repository.HashTagRepository;
import com.hanghae.knowledgesharing.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private HashTagRepository hashTagRepository;

    @InjectMocks
    private ArticleServiceImpl articleService;


    private User mockUser;
    private Article mockArticle;

    @BeforeEach
    void setUp() {
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
        signUpRequestDto.setId("testUser");
        signUpRequestDto.setPassword("password"); // 비밀번호는 실제 테스트에서는 인코딩 과정을 거치게 됩니다.
        signUpRequestDto.setEmail("test@example.com");

        // 공통으로 사용될 User 인스턴스 설정
        mockUser = new User(signUpRequestDto);

        // PostArticleRequestDto 설정
        PostArticleRequestDto postArticleRequestDto = new PostArticleRequestDto();
        postArticleRequestDto.setTitle("Another Title");
        postArticleRequestDto.setContent("내용 테스트");
        postArticleRequestDto.setHashtags(Arrays.asList("hash1", "hash2"));
        postArticleRequestDto.setImageUrls(Arrays.asList("http://image.url"));

        // Article 인스턴스 생성
        mockArticle = new Article(postArticleRequestDto, mockUser);
        ReflectionTestUtils.setField(mockArticle, "id", 1L);


        // 해시태그 추가
        HashTag hashTag1 = new HashTag("hash1");
        HashTag hashTag2 = new HashTag("hash2");
        mockArticle.addHashtag(hashTag1);
        mockArticle.addHashtag(hashTag2);

        // 이미지 추가
        Image image = Image.builder()
                .imageUrl("http://image.url")
                .article(mockArticle)
                .build();

        mockArticle.addImage(image);


    }

    @Test
    @DisplayName("실패 테스트- 게시물 기사: 사용자를 찾을 수 없음")
    void whenUserNotFound_thenThrowException() {

        PostArticleRequestDto requestDto = new PostArticleRequestDto();
        requestDto.setTitle("Another Title");
        requestDto.setContent("내용 테스트");
        requestDto.setHashtags(Arrays.asList("hash1", "hash2"));
        requestDto.setImageUrls(Arrays.asList("http://image.url"));

        // Given
        when(userRepository.findByUserId(anyString())).thenReturn(null);
        // When & Then: articleService.postArticle 호출 시 CustomException이 발생하는지 확인
        CustomException thrown = assertThrows(CustomException.class, () -> articleService.postArticle(requestDto, "user123")); // 존재하지않는 유조
        // 예외 타입이 올바른지 확인
        assertEquals(ErrorCode.UserNotFound, thrown.getErrorCode());

    }

    @Test
    @DisplayName("성공테스트- 글작성")
    void whenPostArticle_thenSuccess() {
        PostArticleRequestDto requestDto = new PostArticleRequestDto();     // 테스트에 사용될 글 작성 요청 DTO를 생성하고 세부 정보(제목, 내용, 해시태그, 이미지 URL)를 설정합니다.

        requestDto.setTitle("Another Title");
        requestDto.setContent("내용 테스트");
        requestDto.setHashtags(Arrays.asList("hash1","hash2"));
        requestDto.setImageUrls(Arrays.asList("http://image.url"));


        when(userRepository.findByUserId(anyString())).thenReturn(mockUser);
        when(hashTagRepository.findByTagName(anyString())).thenReturn(Optional.empty());
        when(articleRepository.save(any(Article.class))).thenAnswer(i -> i.getArguments()[0]);

        // When
        String response = articleService.postArticle(requestDto, "user123");

        // Then
        assertNotNull(response);
        assertEquals("글작성이 성공했습니다", response);
        verify(articleRepository, times(1)).save(any(Article.class)); // 메서드가 정확히 한 번 호출되었는지 검증합니다.
        verify(hashTagRepository, atLeast(1)).findByTagName(anyString());// 메서드가 정확히 한 번 호출되었는지 검증합니다.
    }


    @Test
    @DisplayName("성공 테스트- 게시물 조회")
    void whenGetArticle_thenSuccess() {
        // Given: 주어진 게시물 ID에 대한 mockArticle 객체를 준비합니다.

        Long articleId = 1L;
        when(articleRepository.findById(articleId)).thenReturn(Optional.of(mockArticle));

        when(articleRepository.findById(articleId)).thenReturn(Optional.of(mockArticle)); // ArticleRepository가 해당 ID로 조회될 때 mockArticle을 반환하도록 설정합니다.

        // When
        GetArticleResponseDto responseDto = articleService.getArticle(articleId); // ArticleService의 getArticle 메서드를 호출하여 결과를 가져옵니다.

        // Then
        assertNotNull(responseDto); // 반환된 DTO가 null이 아닌지 확인합니다.
        assertEquals(mockArticle.getId(), responseDto.getId()); // ID가 일치하는지 확인합니다.
//        System.out.println("mockArticle.getId(): " + mockArticle.getId());
//        System.out.println("responseDto: " + responseDto.getId());


        assertEquals(mockArticle.getTitle(), responseDto.getTitle()); // 제목이 일치하는지 확인합니다.
        assertEquals(mockArticle.getContent(), responseDto.getContent()); // 내용이 일치하는지 확인합니다.
        assertEquals(mockUser.getUserId(), responseDto.getWriter()); // 작성자가 일치하는지 확인합니다

        // .
        assertTrue(responseDto.getArticleHashtags().containsAll(Arrays.asList("hash1", "hash2"))); // 해시태그 리스트가 일치하는지 확인합니다.
        assertTrue(responseDto.getImageUrls().containsAll(Arrays.asList("http://image.url"))); // 이미지 URL 리스트가 일치하는지 확인합니다.

    }


    @Test
    @DisplayName("기사 수정 실패 - 사용자 불일치")
    void patchArticleUserMismatch() {
        // Given
        PatchArticleRequestDto requestDto = new PatchArticleRequestDto();
        Long articleId = 1L;
        String wrongUserId = "wrongUser";

        // When & Then
        assertThrows(CustomException.class, () -> articleService.patchArticle(requestDto, articleId, wrongUserId));
    }

}
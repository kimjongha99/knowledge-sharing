package com.hanghae.knowledgesharing.service.impl;


import com.hanghae.knowledgesharing.dto.request.article.PatchArticleRequestDto;
import com.hanghae.knowledgesharing.dto.request.article.PostArticleRequestDto;
import com.hanghae.knowledgesharing.dto.response.ResponseDto;
import com.hanghae.knowledgesharing.dto.response.article.*;
import com.hanghae.knowledgesharing.entity.*;
import com.hanghae.knowledgesharing.repository.ArticleRepository;
import com.hanghae.knowledgesharing.repository.HashTagRepository;
import com.hanghae.knowledgesharing.repository.UserRepository;
import com.hanghae.knowledgesharing.service.ArticleService;
import com.hanghae.knowledgesharing.service.RedisService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl  implements ArticleService {
    private final ArticleRepository articleRepository;
    private final HashTagRepository hashTagRepository;
    private final UserRepository userRepository;

    private final RedisService redisService;


    @Override
    public ResponseEntity<? super PostArticleResponseDto> postArticle(PostArticleRequestDto requestDto, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));


        // 새 생성자를 사용하여 Article 인스턴스를 만듭니다.
        Article article = new Article(requestDto, user);
        // 해시태그 처리
        for (String tagName : requestDto.getHashtags()) {
            HashTag hashTag = hashTagRepository.findByTagName(tagName)
                    .orElseGet(() -> {
                        // 생성자를 사용하여 새 HashTag 인스턴스를 만듭니다.
                        HashTag newHashTag = new HashTag(tagName); // 생성자에 따라 수정합니다.

                        return hashTagRepository.save(newHashTag); //  HashTag를 저장하고 반환합니다.

                    });
            //  생성자를 사용하여 ArticleHashtag 인스턴스를 만듭니다.
            ArticleHashtag articleHashtag = new ArticleHashtag(article, hashTag);
            article.getArticleHashtags().add(articleHashtag); // 기사에 기사해시태그를 추가합니다.

        }
        // Handle images
        if (requestDto.getImageUrls() != null) {
            for (String imageUrl : requestDto.getImageUrls()) {
                Image image = Image.builder()
                        .imageUrl(imageUrl)
                        .article(article) // Associate the image with the article
                        .build();
                article.getImages().add(image); // Add the image to the article's image list
            }
        }
        articleRepository.save(article);
        return PostArticleResponseDto.success();

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

    @Override
    public ResponseEntity<? super PatchArticleResponseDto> patchArticle(PatchArticleRequestDto requestDto, Long articleId, String userId) {
        try {
            // ArticleId로 Article 찾기, 없으면 에러 반환
            Article article = articleRepository.findById(articleId)
                    .orElseThrow(() -> new EntityNotFoundException("Article not found with id: " + articleId));
            // 사용자 존재 여부 확인
            boolean existedUser = userRepository.existsByUserId(userId);
            if (!existedUser) return PatchArticleResponseDto.noExistUser();

            // 권한 검증, 글 작성자와 현재 사용자가 같지 않으면 권한 에러 반환
            if (!article.getUser().getUserId().equals(userId)) {
                return PatchArticleResponseDto.permissionFail();
            }
            // Article 업데이트
            article.patchArticle(requestDto);
            // 해시태그 처리 (기존 해시태그 삭제 후 새로운 해시태그 저장)
            article.getArticleHashtags().clear();
            for (String tagName : requestDto.getHashtags()) {
                HashTag hashTag = hashTagRepository.findByTagName(tagName)
                        .orElseGet(() -> hashTagRepository.save(new HashTag(tagName)));
                article.addHashtag(hashTag);
            }
            // 이미지 처리 (기존 이미지 삭제 후 새로운 이미지 저장)
            article.getImages().clear();
            for (String imageUrl : requestDto.getImageUrls()) {
                Image image = Image.builder()
                        .imageUrl(imageUrl)
                        .article(article)
                        .build();
                article.addImage(image);
            }

            // Article 저장
            articleRepository.save(article);
            return PatchArticleResponseDto.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
    }

    @Override
    public ResponseEntity<? super DeleteArticleResponseDto> deleteArticle(Long articleId, String userId) {
        try {
            Article article = articleRepository.findById(articleId)
                    .orElseThrow(() -> new EntityNotFoundException("Article not found with id: " + articleId));

            boolean existedUser = userRepository.existsByUserId(userId);
            if (!existedUser) {
                return DeleteArticleResponseDto.noExistUser();
            }

            if (!article.getUser().getUserId().equals(userId)) {
                return DeleteArticleResponseDto.permissionFail();
            }

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
    public ResponseEntity<? super ArticleListResponseDto> getArticleList(Pageable pageable) {
        try {
            Page<Article> articlesPage = articleRepository.findAll(pageable);


            Page<ArticleListResponseDto.ArticleDetailDto> articleDetailsPage = articlesPage.map(article -> {
                List<String> hashtags = article.getArticleHashtags().stream().map(ArticleHashtag::getHashtagName).collect(Collectors.toList());
                List<String> imageUrls = article.getImages().stream().map(Image::getImageUrl).collect(Collectors.toList());
                return new ArticleListResponseDto.ArticleDetailDto(
                        article.getId(),
                        article.getTitle(),
                        article.getFavoriteCount(),
                        article.getViewCount(),
                        hashtags,
                        imageUrls,
                        article.getUser().getUserId() // Assuming User has getUserId() method
                );
            });

            return ArticleListResponseDto.success(articleDetailsPage);


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
    }


    //조회수 증가
    @Override
    public ResponseEntity<ArticleViewCountResponseDto> incrementArticleViewCount(String userId, Long articleId) {
        try {
            boolean isFirstView = redisService.checkAndSetArticleView(articleId, userId);
            Article article = articleRepository.findById(articleId)
                    .orElseThrow(() -> new EntityNotFoundException("Article not found with id: " + articleId));
            if (isFirstView) { // USER가 처음 볼때. 조회수 증가
                article.incrementViewCount();
                articleRepository.save(article);
            }
            return ArticleViewCountResponseDto.success("SU", "View count updated successfully");
        } catch (EntityNotFoundException e) {
            return ArticleViewCountResponseDto.databaseError("NOT_FOUND_CODE", "Article not found");
        } catch (Exception e) {
            return ArticleViewCountResponseDto.databaseError("DATABASE_ERROR_CODE", "Database error occurred");
        }


    }
}
























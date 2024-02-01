package com.hanghae.knowledgesharing.service.impl;


import com.hanghae.knowledgesharing.dto.request.article.PostArticleRequestDto;
import com.hanghae.knowledgesharing.dto.response.ResponseDto;
import com.hanghae.knowledgesharing.dto.response.article.GetBoardResponseDto;
import com.hanghae.knowledgesharing.dto.response.article.PostArticleResponseDto;
import com.hanghae.knowledgesharing.entity.*;
import com.hanghae.knowledgesharing.repository.ArticleRepository;
import com.hanghae.knowledgesharing.repository.HashTagRepository;
import com.hanghae.knowledgesharing.repository.UserRepository;
import com.hanghae.knowledgesharing.service.ArticleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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


    @Override
    public ResponseEntity<? super PostArticleResponseDto> postArticle(PostArticleRequestDto requestDto, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));


        // 새 생성자를 사용하여 Article 인스턴스를 만듭니다.
        Article article = new Article(requestDto, user );
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
    public ResponseEntity<? super GetBoardResponseDto> getArticle(Long ArticleId) {

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
            articleHashtags  = article.getArticleHashtags()
                    .stream()
                    .map(ArticleHashtag::getHashtagName) // 가정: ArticleHashtag 엔티티에 getHashtagName 메소드가 있다.
                    .collect(Collectors.toList());

            imageUrls  = article.getImages()
                    .stream()
                    .map(Image::getImageUrl) // 가정: Image 엔티티에 getImageUrl 메소드가 있다.
                    .collect(Collectors.toList());


            return GetBoardResponseDto.success(id, title, content, writer, favoriteCount, viewCount, articleHashtags, imageUrls);


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();

        }


        }




}

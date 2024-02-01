package com.hanghae.knowledgesharing.service.impl;


import com.hanghae.knowledgesharing.dto.request.article.PostArticleRequestDto;
import com.hanghae.knowledgesharing.dto.response.article.PostArticleResponseDto;
import com.hanghae.knowledgesharing.entity.*;
import com.hanghae.knowledgesharing.repository.ArticleRepository;
import com.hanghae.knowledgesharing.repository.HashTagRepository;
import com.hanghae.knowledgesharing.repository.UserRepository;
import com.hanghae.knowledgesharing.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
}

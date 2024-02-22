package com.hanghae.knowledgesharing.article.sevice.Impl;

import com.hanghae.knowledgesharing.article.dto.request.PatchArticleRequestDto;
import com.hanghae.knowledgesharing.article.dto.request.PostArticleRequestDto;
import com.hanghae.knowledgesharing.article.dto.request.UpdateFavoriteCountRequestDto;
import com.hanghae.knowledgesharing.article.dto.response.*;
import com.hanghae.knowledgesharing.article.repository.ArticleRepository;
import com.hanghae.knowledgesharing.article.sevice.ArticleService;
import com.hanghae.knowledgesharing.common.entity.*;
import com.hanghae.knowledgesharing.common.exception.CustomException;
import com.hanghae.knowledgesharing.common.exception.ErrorCode;
import com.hanghae.knowledgesharing.common.util.redis.RedisService;
import com.hanghae.knowledgesharing.hashtag.repository.HashTagRepository;
import com.hanghae.knowledgesharing.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    private final RedisService redisService;
    private final HashTagRepository hashTagRepository;

    @Override
    public String postArticle(PostArticleRequestDto requestDto, String userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new CustomException(ErrorCode.UserNotFound);
        }
        ;

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
            // 이미지 처리
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
        return ("글작성이 성공했습니다");

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
    public PatchArticleResponseDto patchArticle(PatchArticleRequestDto requestDto, Long articleId, String userId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new CustomException(ErrorCode.ArticleNotFound));
        if (!userRepository.existsByUserId(userId)) {
            throw new CustomException(ErrorCode.UserNotFound);
        }

        if (!article.getUser().getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.PermissionDenied);
        }

        article.patchArticle(requestDto); // Assume this method updates the article's fields from requestDto
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
        return new PatchArticleResponseDto("수정 성공");

    }

    @Override
    public DeleteArticleResponseDto deleteArticle(Long articleId, String userId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new CustomException(ErrorCode.ArticleNotFound));

        if (!userRepository.existsByUserId(userId)) {
            throw new CustomException(ErrorCode.UserNotFound);
        }

        if (!article.getUser().getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.PermissionDenied);
        }

        // Delete the article
        articleRepository.delete(article);
        return new DeleteArticleResponseDto("Article deleted successfully");
    }

    @Override
    public Page<ArticleListResponseDto.ArticleDetailDto> getArticleList(Pageable pageable) {
        Page<Article> articlesPage = articleRepository.findAll(pageable);

        Page<ArticleListResponseDto.ArticleDetailDto> articleDetailsPage = articlesPage.map(article -> {
            List<String> hashtags = article.getArticleHashtags().stream()
                    .map(ArticleHashtag::getHashtagName)
                    .collect(Collectors.toList());
            List<String> imageUrls = article.getImages().stream()
                    .map(Image::getImageUrl)
                    .collect(Collectors.toList());
            return new ArticleListResponseDto.ArticleDetailDto(
                    article.getId(),
                    article.getTitle(),
                    article.getFavoriteCount(),
                    article.getViewCount(),
                    hashtags,
                    imageUrls,
                    article.getUser().getUserId() // 가정: User 엔티티에 getUserId() 메서드가 있음
            );
        });
        return  articleDetailsPage;

    }

    @Override
    public ArticleViewCountResponseDto incrementArticleViewCount(String userId, Long articleId) {
        boolean isFirstView = redisService.checkAndSetArticleView(articleId, userId);

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new CustomException(ErrorCode.ArticleNotFound));
        if (isFirstView) {
            article.incrementViewCount();
            articleRepository.save(article);
        }
        return new ArticleViewCountResponseDto("조회수 증가 성공");

    }
//    boolean isFavorite = redisService.likeArticle(articleId,userId);

    @Override
    public UpdateFavoriteCountResponseDto updateFavoriteCount(Long articleId, UpdateFavoriteCountRequestDto requestDto , String userId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new CustomException(ErrorCode.ArticleNotFound));

        switch (requestDto.getActionType()) {
            case INCREMENT:
                if (redisService.likeArticle(articleId, userId)) {
                    article.increaseFavoriteCount();
                } else {
                    throw new CustomException(ErrorCode.AlreadyLiked);
                }
                break;
            case DECREMENT:
                if (redisService.unlikeArticle(articleId, userId)) {
                    int currentCount = article.getFavoriteCount();
                    if (currentCount > 0) {
                        article.decreaseFavoriteCount(currentCount);
                    }
                } else {
                    throw new CustomException(ErrorCode.NotLiked);
                }
                break;
            default:
                throw new CustomException(ErrorCode.InvalidActionType);
        }

        articleRepository.save(article);
        return new UpdateFavoriteCountResponseDto("좋아요 요청 성공");
    }

    @Override
    public List<Top3ArticleResponseDto> getTopFavoriteArticles() {
        List<Article> articles = articleRepository.findTop3FavoriteArticlesFromLastWeek();
        return articles.stream()
                .map(Top3ArticleResponseDto::fromArticle)
                .collect(Collectors.toList());
    }



}




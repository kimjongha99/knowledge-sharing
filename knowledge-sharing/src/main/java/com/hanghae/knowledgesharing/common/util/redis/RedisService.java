package com.hanghae.knowledgesharing.common.util.redis;

public interface RedisService {
    boolean checkAndSetArticleView(Long articleId, String userId);

    boolean likeArticle(Long articleId, String userId);

    boolean unlikeArticle(Long articleId, String userId);
}
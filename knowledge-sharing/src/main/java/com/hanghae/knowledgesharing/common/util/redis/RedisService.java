package com.hanghae.knowledgesharing.common.util.redis;

public interface RedisService {
    boolean checkAndSetArticleView(Long articleId, String userId);
}
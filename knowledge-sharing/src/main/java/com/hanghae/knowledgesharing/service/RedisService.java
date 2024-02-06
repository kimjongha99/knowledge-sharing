package com.hanghae.knowledgesharing.service;

public interface RedisService {
    boolean checkAndSetArticleView(Long articleId, String userId);
}
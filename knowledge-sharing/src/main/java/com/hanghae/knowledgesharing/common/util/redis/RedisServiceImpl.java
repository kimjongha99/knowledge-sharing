package com.hanghae.knowledgesharing.common.util.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, Object> redisTemplate;


    @Override
    public boolean checkAndSetArticleView(Long articleId, String userId) {
        String key = "viewCount:article_" + articleId;
        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
        boolean alreadyViewed = redisTemplate.hasKey(key);
//        System.out.println(key + alreadyViewed);
        if (!alreadyViewed) {
            valueOps.set(key, "viewed", 1, TimeUnit.HOURS);

            return true;
        }
        return false;
    }
}
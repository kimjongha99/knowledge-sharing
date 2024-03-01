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
        String key = "viewCount:article_" + articleId + ":user_" + userId;  //기사별로 유저 조회수로 리펙토링
        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
        boolean alreadyViewed = redisTemplate.hasKey(key);
        if (!alreadyViewed) {
            valueOps.set(key, "viewed", 1, TimeUnit.HOURS);

            return true;
        }
        return false;
    }



    @Override
    public boolean likeArticle(Long articleId, String userId) {
        String key = "likes:article_" + articleId;
        Boolean alreadyLiked = redisTemplate.opsForSet().isMember(key, userId); //isMember 메소드를 호출하여 특정 사용자가 이미 해당 기사를 '좋아요' 했는지 확인합니다.
        if (Boolean.TRUE.equals(alreadyLiked)) {
            return false;// 사용자가 이미 기사에 좋아요를 표시했습니다. 조치가 필요하지 않습니다.
        } else {
            redisTemplate.opsForSet().add(key, userId);
            redisTemplate.expire(key, 1, TimeUnit.HOURS); // 1시간 후에 키가 만료되도록 설정r

            return true; //좋아요 추가됨
        }
    }


    @Override
    public boolean unlikeArticle(Long articleId, String userId) {
        String key = "unlikes:article_" + articleId;
        Boolean alreadyUnLiked = redisTemplate.opsForSet().isMember(key, userId); //isMember 메소드를 호출하여 특정 사용자가 이미 해당 기사를 '좋아요' 했는지 확인합니다.
        if (Boolean.TRUE.equals(alreadyUnLiked)) {
            return false;// 사용자가 이미 기사에 싫어요  표시했습니다. 조치가 필요하지 않습니다.
        } else {
            redisTemplate.opsForSet().add(key, userId);
            redisTemplate.expire(key, 1, TimeUnit.HOURS); // 1시간 후에 키가 만료되도록 설정r
            return true; //싫어요 추가됨
        }
    }



}
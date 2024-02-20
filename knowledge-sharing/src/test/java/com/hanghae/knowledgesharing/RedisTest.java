//package com.hanghae.knowledgesharing;
//
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.test.context.junit4.SpringRunner;
//
//-
//@SpringBootTest
//@RunWith(SpringRunner.class)
//public class RedisTest {
//
//
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
//
//
//    @Test
//    public void testRedisSetAndGet() {
//        String key = "testKey";
//        String value = "testValue";
//
//        // Set a key-value pair
//        redisTemplate.opsForValue().set(key, value);
//
//        // Retrieve the value by key
//        Object retrievedValue = redisTemplate.opsForValue().get(key);
//
//        // Assert that the value matches what was set
//        assertEquals(value, retrievedValue);
//    }
//
//    @Test
//    public void testIncrementViewCount() {
//        String articleId = "article_123"; // Simulate an article ID
//        String viewCountKey = "viewCount:" + articleId; // Redis key for storing view count
//
//        // Simulate viewing the article 3 times
//        for (int i = 0; i < 3; i++) {
//            redisTemplate.opsForValue().increment(viewCountKey);
//        }
//
//        // Retrieve the view count from Redis and convert to Integer
//        String viewCountStr = (String) redisTemplate.opsForValue().get(viewCountKey);
//        Integer viewCount = Integer.parseInt(viewCountStr);
//
//        // Assert that the view count is 3
//        assertEquals(Integer.valueOf(3), viewCount);
//    }
//}
//

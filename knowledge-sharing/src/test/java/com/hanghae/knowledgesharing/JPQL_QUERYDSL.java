package com.hanghae.knowledgesharing;

import com.hanghae.knowledgesharing.common.entity.User;
import com.hanghae.knowledgesharing.article.repository.ArticleRepository;
import com.hanghae.knowledgesharing.user.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class JPQL_QUERYDSL {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void testFindArticleWithTitle() {
        String name = "kakao_3322440333"; // Adjust as necessary for your test case
        List<User> users = userRepository.findUsersByNameContaining(name);

        System.out.println(users);
    }
}

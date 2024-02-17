package com.hanghae.knowledgesharing;

import com.hanghae.knowledgesharing.article.dto.request.PostArticleRequestDto;
import com.hanghae.knowledgesharing.article.sevice.ArticleService;
import com.hanghae.knowledgesharing.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class KnowledgeSharingApplicationTests {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserRepository userRepository; // Assuming you have a user repository
    @Test
    public void insertTestData() {
        // Create a User object
        String userId = "test00";


        // Persist the User object if necessary
        // userRepository.save(user); // Uncomment if you need to persist the new User

        for (int i = 1; i <= 100; i++) {
            // Create a new PostArticleRequestDto instance
            PostArticleRequestDto requestDto = new PostArticleRequestDto();

            // Populate the DTO with test data
            requestDto.setTitle("Test Title " + i);
            requestDto.setContent("Test content for article " + i);
            requestDto.setHashtags(Arrays.asList("hashtag1", "hashtag2")); // Modify as needed
            requestDto.setImageUrls(Arrays.asList("image" + i + ".jpg", "image" + (i + 1) + ".jpg")); // Modify as needed

            // Call the postArticle method
//            ResponseEntity<? super PostArticleResponseDto> response = articleService.postArticle(requestDto, userId); // Ensure the articleService is properly initialized

            // Optional: Print out a message or log the result
            System.out.println("Inserted article " + i);
        }
    }

    @Test
    void contextLoads() {
    }

}

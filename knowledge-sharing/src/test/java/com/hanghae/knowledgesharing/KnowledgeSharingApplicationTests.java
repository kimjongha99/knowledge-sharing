//package com.hanghae.knowledgesharing;
//
//import com.hanghae.knowledgesharing.article.dto.request.PostArticleRequestDto;
//import com.hanghae.knowledgesharing.article.sevice.ArticleService;
//import com.hanghae.knowledgesharing.user.repository.UserRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Arrays;
//
//@SpringBootTest
//class KnowledgeSharingApplicationTests {
//    @Autowired
//    private ArticleService articleService;
//
//    @Autowired
//    private UserRepository userRepository; // Assuming you have a user repository
//
//
//
//
//
//
//    @Test
//    public void insertTestData() {
//
//        for (int i = 200; i <= 300; i++) {
//
//            String userId = "test" + i;
//
//            // Create a new PostArticleRequestDto instance
//            PostArticleRequestDto requestDto = new PostArticleRequestDto();
//
//            // Populate the DTO with test data
//            requestDto.setTitle("Test Title " + i);
//            requestDto.setContent("Test content for article " + i);
//            requestDto.setHashtags(Arrays.asList("hashtag1", "hashtag2")); // Modify as needed
//            requestDto.setImageUrls(Arrays.asList("https://knowledge-sharing-jong.s3.ap-northeast-2.amazonaws.com/user/005b9fcb-c473-43b8-b1ac-862fff2149f4.jpg")); // Modify as needed
//
//            String response = articleService.postArticle(requestDto, userId); // Ensure the articleService is properly initialized
//
//            System.out.println("Inserted article " + i+ "\n"+response);
//        }
//    }
//
//    @Test
//    void contextLoads() {
//    }
//
//}

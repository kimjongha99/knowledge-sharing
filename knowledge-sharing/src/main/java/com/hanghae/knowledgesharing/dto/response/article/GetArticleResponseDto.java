package com.hanghae.knowledgesharing.dto.response.article;

import com.hanghae.knowledgesharing.common.ResponseCode;
import com.hanghae.knowledgesharing.common.ResponseMessage;
import com.hanghae.knowledgesharing.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;


@Getter
public class GetArticleResponseDto extends ResponseDto {

    private  Long id;

    private  String title;

    private  String content;

    private  String writer;

    private int favoriteCount;
    private int viewCount;

    private List<String> articleHashtags;

    private  List<String>  imageUrls;


    private GetArticleResponseDto(Long id, String title, String content, String writer, int favoriteCount, int viewCount, List<String> articleHashtags, List<String> imageUrls) {
        super();
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.favoriteCount = favoriteCount;
        this.viewCount = viewCount;
        this.articleHashtags = articleHashtags;
        this.imageUrls = imageUrls;
    }


    public static ResponseEntity<GetArticleResponseDto> success(Long id, String title, String content, String writer, int favoriteCount, int viewCount, List<String> articleHashtags, List<String> imageUrls) {
        GetArticleResponseDto responseBody = new GetArticleResponseDto(id, title, content, writer, favoriteCount, viewCount, articleHashtags, imageUrls);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }


    public static ResponseEntity<ResponseDto> GetArticleFail() {
        ResponseDto result = new ResponseDto(ResponseCode.ARTICLE_FAIL, ResponseMessage.ARTICLE_FAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}

package com.hanghae.knowledgesharing.user.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleInfo {

    private  Long id;
    private  String title;
    private  int favoriteCount;
    private  int viewCount;
}

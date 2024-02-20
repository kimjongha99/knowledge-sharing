package com.hanghae.knowledgesharing.user.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserArticleResponseDto {
    private List<ArticleInfo> users;
    private int page;
    private int size;
    private  Long totalElements;

}

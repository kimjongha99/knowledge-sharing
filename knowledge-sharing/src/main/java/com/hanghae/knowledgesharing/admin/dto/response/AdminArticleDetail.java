package com.hanghae.knowledgesharing.admin.dto.response;

import com.hanghae.knowledgesharing.common.enums.HashTagTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
public class AdminArticleDetail {

    private Long id;

    private String title;

    private String writer;

    private String content;

    private HashTagTypeEnum tagType;

    private int favoriteCount;

    private int viewCount;

    private List<String> hashtag;

    public AdminArticleDetail(Long id, String title, String writer, String content, HashTagTypeEnum tagType, int favoriteCount, int viewCount, List<String> hashtag) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.tagType = tagType;
        this.favoriteCount = favoriteCount;
        this.viewCount = viewCount;
        this.hashtag = hashtag;
    }


}

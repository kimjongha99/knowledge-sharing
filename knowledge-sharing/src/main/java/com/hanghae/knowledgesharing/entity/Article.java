package com.hanghae.knowledgesharing.entity;


import com.hanghae.knowledgesharing.dto.request.article.PostArticleRequestDto;
import com.hanghae.knowledgesharing.enums.HashTagTypeEnum;
import com.hanghae.knowledgesharing.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity(name="article")
@Table(name="article")
@AllArgsConstructor
public class Article extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false) //컬럼의 길이와 null허용여부
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(length = 50, nullable = false)
    private String writer;

    private HashTagTypeEnum tagType;

    private int favoriteCount;

    private int viewCount;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleHashtag> articleHashtags = new ArrayList<>();


    public Article(PostArticleRequestDto requestDto, String userId) {
        this.title = requestDto.getTitle();
        this.content= requestDto.getContent();
        this.writer = userId;
        this.tagType= HashTagTypeEnum.ARTICLE_TAG;
        this.favoriteCount =0;
        this.viewCount=0;
    }
}
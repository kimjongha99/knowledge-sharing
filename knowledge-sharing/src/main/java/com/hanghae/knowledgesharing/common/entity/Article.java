package com.hanghae.knowledgesharing.common.entity;


import com.hanghae.knowledgesharing.article.dto.request.article.PatchArticleRequestDto;
import com.hanghae.knowledgesharing.article.dto.request.article.PostArticleRequestDto;
import com.hanghae.knowledgesharing.common.enums.HashTagTypeEnum;
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

    @Column(name = "tag_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private HashTagTypeEnum tagType;

    private int favoriteCount;

    private int viewCount;
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleHashtag> articleHashtags = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // 이 컬럼은 'article' 테이블에 있습니다.
    private User user;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();


    public Article(PostArticleRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.content= requestDto.getContent();
        this.user = user;
        this.tagType= HashTagTypeEnum.ARTICLE_TAG;
        this.favoriteCount =0;
        this.viewCount=0;
    }


    public void patchArticle(PatchArticleRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

    public void addHashtag(HashTag hashTag) {
        ArticleHashtag articleHashtag = new ArticleHashtag(this, hashTag);
        this.articleHashtags.add(articleHashtag);

    }

    public void addImage(Image image) {
        this.images.add(image);
        image.setArticle(this);
    }

    public void incrementViewCount() {
        this.viewCount++;

    }

    public void increaseFavoriteCount() {
        this.favoriteCount++;
    }

    public void decreaseFavoriteCount(int currentCount) {
        this.favoriteCount= currentCount-1;
    }

}
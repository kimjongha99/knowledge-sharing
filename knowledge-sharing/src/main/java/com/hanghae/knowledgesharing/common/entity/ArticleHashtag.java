package com.hanghae.knowledgesharing.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "article_hashtag")
public class ArticleHashtag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id", nullable = false)
    private HashTag hashtag;


    public ArticleHashtag(Article article, HashTag hashTag) {
        this.article = article;
        this.hashtag = hashTag;
    }


    // 해시태그 이름을 반환하는 메서드
    public String getHashtagName() {
        return this.hashtag.getName(); // 가정: HashTag 엔티티에 getName 메소드가 있다.
    }
}

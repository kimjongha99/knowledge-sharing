package com.hanghae.knowledgesharing.common.entity;


import com.hanghae.knowledgesharing.common.enums.HashTagTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "hashtag")
@Table(name = "hashtag")
public class HashTag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String tagName;

    @Enumerated(EnumType.STRING)
    private HashTagTypeEnum tagType;

    @OneToMany(mappedBy = "hashtag", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleHashtag> articleHashtags = new ArrayList<>();

    // Regular constructor
    public HashTag(String tagName) {
        this.tagName = tagName;
        this.tagType = HashTagTypeEnum.ARTICLE_TAG;
        this.articleHashtags = new ArrayList<>();
    }

    public String getName() {
        return this.tagName;
    }

}
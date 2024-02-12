package com.hanghae.knowledgesharing.enums;

import lombok.Getter;

@Getter
public enum HashTagTypeEnum {

    ARTICLE_TAG(TagType.ARTICLE, "게시글태그"), // Article tag
    QUIZ_TAG(TagType.QUIZ, "퀴즈태그"); // Quiz tag

    private final String tagType;
    private final String description;

    HashTagTypeEnum(String tagType, String description) {
        this.tagType = tagType;
        this.description = description;
    }

    public static class TagType {
        public static final String ARTICLE = "게시글태그";
        public static final String QUIZ = "퀴즈태그";
    }
}


//HashTagTypeEnum articleTag = HashTagTypeEnum.ARTICLE_TAG;
//System.out.println(articleTag.getTagType()); // Output: ARTICLE_TAG
//System.out.println(articleTag.getDescription()); // Output: Article Tag
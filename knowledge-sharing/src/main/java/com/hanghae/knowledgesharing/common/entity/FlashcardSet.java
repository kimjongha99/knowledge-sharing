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
@Entity
@Table(name = "flashcard_set")
public class FlashcardSet extends BaseTimeEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;


    @Column(name = "tag_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private HashTagTypeEnum tagType;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "flashcardSet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Flashcard> flashcards = new ArrayList<>();

    @OneToMany(mappedBy = "flashcardSet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CardHashTag> cardHashTags = new ArrayList<>();

    // 직접 정의한 생성자
    public FlashcardSet(String title, String description, HashTagTypeEnum tagType, User user) {
        this.title = title;
        this.description = description;
        this.tagType = tagType;
        this.user = user;
        // flashcards와 cardHashTags는 빈 리스트로 초기화되어 있으므로 별도의 초기화 필요 없음
    }


    public void setDescription(String description) {
        this.description=description;
    }

    public void setTitle(String title) {
        this.title=title;
    }
}

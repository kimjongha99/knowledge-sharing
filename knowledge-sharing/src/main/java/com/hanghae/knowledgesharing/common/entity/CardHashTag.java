package com.hanghae.knowledgesharing.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "card_hashtag")
public class CardHashTag extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flashcard_set_id", nullable = false)
    private FlashcardSet flashcardSet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id", nullable = false)
    private HashTag hashtag;



    public CardHashTag(FlashcardSet flashcardSet, HashTag hashTag) {
        this.flashcardSet = flashcardSet;
        this.hashtag = hashTag;
    }
    public String getHashtagName() {
        return this.hashtag.getName(); // 가정: HashTag 엔티티에 getName 메소드가 있다.
    }

}

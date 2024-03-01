package com.hanghae.knowledgesharing.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flashcard")
public class Flashcard extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flashcard_set_id")
    private FlashcardSet flashcardSet;

    @Column(name = "term", nullable = false, length = 255)
    private String term;

    @Column(name = "definition", columnDefinition = "TEXT")
    private String definition;


    // 새로운 생성자 추가
    public Flashcard(String term, String definition, FlashcardSet flashcardSet) {
        this.term = term;
        this.definition = definition;
        this.flashcardSet = flashcardSet; // Flashcard가 속하는 FlashcardSet 설정
    }
}

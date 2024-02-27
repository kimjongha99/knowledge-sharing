package com.hanghae.knowledgesharing.quiz.repository;

import com.hanghae.knowledgesharing.common.entity.Flashcard;
import com.hanghae.knowledgesharing.common.queryDsl.quiz.QuizRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  CardRepository extends JpaRepository<Flashcard, Long> , QuizRepositoryCustom {
    @Query("SELECT f FROM Flashcard f WHERE f.flashcardSet.id = :cardSetId")
    List<Flashcard> findByFlashcardSetId(@Param("cardSetId") Long cardSetId);

}

package com.hanghae.knowledgesharing.cardSet.repository;


import com.hanghae.knowledgesharing.common.entity.FlashcardSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardSetRepository extends JpaRepository<FlashcardSet, Long> {
}

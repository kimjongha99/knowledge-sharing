package com.hanghae.knowledgesharing.cardSet.repository;


import com.hanghae.knowledgesharing.common.entity.FlashcardSet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardSetRepository extends JpaRepository<FlashcardSet, Long> {

    Optional<FlashcardSet> findById(Long id);
    @Query("SELECT f FROM FlashcardSet f WHERE " +
            "(:title IS NULL OR :title = '' OR f.title LIKE CONCAT('%', :title, '%')) AND " +
            "(:description IS NULL OR :description = '' OR f.description LIKE CONCAT('%', :description, '%'))")
    Page<FlashcardSet> getFlashcardSets(@Param("title") String title, @Param("description") String description, Pageable pageable);

}

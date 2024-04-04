package com.hanghae.knowledgesharing.comment.repository;

import com.hanghae.knowledgesharing.common.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM comment c JOIN FETCH c.article WHERE c.article.id = :articleId")
    Page<Comment> findAllByArticleId(Long articleId, Pageable pageable);
}

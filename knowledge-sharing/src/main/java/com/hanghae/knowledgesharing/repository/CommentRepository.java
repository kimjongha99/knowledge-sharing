package com.hanghae.knowledgesharing.repository;


import com.hanghae.knowledgesharing.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {


    Page<Comment> findAllByArticleId(Long articleId, Pageable pageable);
}

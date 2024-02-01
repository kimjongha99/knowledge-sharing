package com.hanghae.knowledgesharing.repository;

import com.hanghae.knowledgesharing.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {
}

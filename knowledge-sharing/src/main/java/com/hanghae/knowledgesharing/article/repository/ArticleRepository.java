package com.hanghae.knowledgesharing.article.repository;


import com.hanghae.knowledgesharing.common.entity.Article;
import com.hanghae.knowledgesharing.common.queryDsl.article.ArticleRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> , ArticleRepositoryCustom {

    @Query("SELECT a FROM article a LEFT JOIN a.articleHashtags ah WHERE " +
            "(:title IS NULL OR a.title LIKE %:title%) AND " +
            "(:content IS NULL OR a.content LIKE %:content%) AND " +
            "(:hashtag IS NULL OR ah.hashtag.tagName LIKE %:hashtag%)")
    Page<Article> findByTitleAndContentAndHashtagName(@Param("title") String title,
                                                      @Param("content") String content,
                                                      @Param("hashtag") String hashtag,
                                                      Pageable pageable);



}

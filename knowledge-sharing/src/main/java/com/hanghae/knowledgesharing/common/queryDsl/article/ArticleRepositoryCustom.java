package com.hanghae.knowledgesharing.common.queryDsl.article;

import com.hanghae.knowledgesharing.common.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ArticleRepositoryCustom {
    Page<Article> getUserArticles(String userId, Pageable pageable);
}

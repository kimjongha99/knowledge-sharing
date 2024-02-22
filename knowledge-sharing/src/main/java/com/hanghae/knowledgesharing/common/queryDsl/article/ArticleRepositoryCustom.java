package com.hanghae.knowledgesharing.common.queryDsl.article;

import com.hanghae.knowledgesharing.common.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ArticleRepositoryCustom {
    Page<Article> getUserArticles(String userId, Pageable pageable);

    List<Article> findTop3FavoriteArticlesFromLastWeek();
}

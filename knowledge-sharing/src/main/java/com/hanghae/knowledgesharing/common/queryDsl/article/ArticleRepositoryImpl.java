package com.hanghae.knowledgesharing.common.queryDsl.article;

import com.hanghae.knowledgesharing.common.entity.Article;
import com.hanghae.knowledgesharing.common.entity.QArticle;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Page<Article> getUserArticles(String userId, Pageable pageable) {
        QArticle qArticle = QArticle.article;

        // 결과 리스트 조회 쿼리
        List<Article> articles = jpaQueryFactory
                .selectFrom(qArticle)
                .where(qArticle.user.userId.eq(userId))
                .orderBy(qArticle.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        // 총 아티클 수 조회
        // 총 개수 조회 쿼리
        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(qArticle.count())
                .from(qArticle)
                .where(qArticle.user.userId.eq(userId));


        // PageableExecutionUtils.getPage()를 사용하여 Page 객체 생성
        return PageableExecutionUtils.getPage(articles, pageable, countQuery::fetchOne);

    }


    @Override
    public List<Article> findTop3FavoriteArticlesFromLastWeek() {
        QArticle qArticle = QArticle.article;
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);

        return jpaQueryFactory
                .selectFrom(qArticle)
                .where(qArticle.createdAt.after(oneWeekAgo))
                .orderBy(qArticle.favoriteCount.desc())
                .limit(3)
                .fetch();
    }
}

package com.hanghae.knowledgesharing.common.queryDsl.user;

import com.hanghae.knowledgesharing.common.entity.QUser;
import com.hanghae.knowledgesharing.common.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl  implements UserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Page<User> findByType(String type, Pageable pageable) {
        QUser qUser = QUser.user;

        List<User> users = jpaQueryFactory
                .selectFrom(qUser)
                .where(qUser.type.eq(type))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        long total = jpaQueryFactory
                .select(qUser.count())
                .from(qUser)
                .where(qUser.type.eq(type))
                .fetchOne();

        return new PageImpl<>(users, pageable, total);

    }
}
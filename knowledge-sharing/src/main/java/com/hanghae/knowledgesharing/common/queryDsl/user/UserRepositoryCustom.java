package com.hanghae.knowledgesharing.common.queryDsl.user;

import com.hanghae.knowledgesharing.common.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {

    Page<User> findByType(String type, Pageable pageable);
}

package com.hanghae.knowledgesharing.follow.repository;


import com.hanghae.knowledgesharing.common.entity.Follow;
import com.hanghae.knowledgesharing.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFromUserAndToUser(User fromUser, User toUser);

}

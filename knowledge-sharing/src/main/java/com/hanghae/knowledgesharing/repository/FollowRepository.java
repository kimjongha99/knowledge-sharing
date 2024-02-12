package com.hanghae.knowledgesharing.repository;


import com.hanghae.knowledgesharing.entity.Follow;
import com.hanghae.knowledgesharing.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFromUserAndToUser(User fromUser, User toUser);

}

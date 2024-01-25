package com.hanghae.knowledgesharing.repository;

import com.hanghae.knowledgesharing.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUserId(String userId);

    boolean existsByUserId(String userId);
}

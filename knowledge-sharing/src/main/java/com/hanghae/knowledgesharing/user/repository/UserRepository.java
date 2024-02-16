package com.hanghae.knowledgesharing.user.repository;

import com.hanghae.knowledgesharing.common.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUserId(String userId);

    boolean existsByUserId(String userId);



    Page<User> findByUserIdContaining(String userId, Pageable pageable);
    Page<User> findByEmailContaining(String email, Pageable pageable);


}

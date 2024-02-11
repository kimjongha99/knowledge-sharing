package com.hanghae.knowledgesharing.repository;

import com.hanghae.knowledgesharing.entity.User;
import com.hanghae.knowledgesharing.enums.UserRoleEnum;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUserId(String userId);


    boolean existsByUserId(String userId);

    @Transactional
    @Modifying
    @Query("UPDATE user u SET u.role = :role WHERE u.userId = :userId")
    int updateUserRole(@Param("userId") String userId, @Param("role") UserRoleEnum role);

    Page<User> findByUserIdContaining(String userId, Pageable pageable);
    Page<User> findByEmailContaining(String email, Pageable pageable);

}

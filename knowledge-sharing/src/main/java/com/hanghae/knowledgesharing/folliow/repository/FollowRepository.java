package com.hanghae.knowledgesharing.folliow.repository;


import com.hanghae.knowledgesharing.common.entity.Follow;
import com.hanghae.knowledgesharing.common.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    // fromUser 필드가 지정된 User 개체와 일치하는 모든 Follow 엔터티를 가져옵니다. 해당 사용자가 팔로우하는 사용자를 나타냅니다.
    @Query("SELECT f FROM follow f WHERE f.fromUser = :user")
    Page<Follow> findAllByFromUser(@Param("user") User user, Pageable pageable);


    // toUser 필드가 지정된 User 개체와 일치하는 모든 Follow 엔터티를 가져옵니다. 해당 사용자를 팔로우하는 사용자를 나타냅니다.
    @Query("SELECT f FROM follow f WHERE f.toUser = :user")
    Page<Follow> findAllByToUser(@Param("user") User user,Pageable pageable);


    boolean existsByFromUserAndToUser(User follower, User following);

    void deleteByFromUserAndToUser(User follower, User following);
}

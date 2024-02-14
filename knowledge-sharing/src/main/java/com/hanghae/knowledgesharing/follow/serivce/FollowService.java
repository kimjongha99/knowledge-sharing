package com.hanghae.knowledgesharing.follow.serivce;

import com.hanghae.knowledgesharing.follow.dto.response.CheckFollowResponseDto;
import com.hanghae.knowledgesharing.follow.dto.response.FollowingUserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface FollowService {
    ResponseEntity<CheckFollowResponseDto> checkFollow(String followerUserId, String followingUserId );

    Page<FollowingUserResponseDto> getFollowingUsers(String userId, Pageable pageable);
    Page<FollowingUserResponseDto> getFollowers(String userId, Pageable pageable);

}

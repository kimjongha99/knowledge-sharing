package com.hanghae.knowledgesharing.folliow.sevice;

import com.hanghae.knowledgesharing.folliow.dto.response.CheckFollowResponseDto;
import com.hanghae.knowledgesharing.folliow.dto.response.FollowerUserResponseDto;
import com.hanghae.knowledgesharing.folliow.dto.response.FollowingUserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FollowService {
    CheckFollowResponseDto checkFollow(String followId, String followingId);

    Page<FollowingUserResponseDto> getFollowingUsers(String userId, Pageable pageable);

    Page<FollowerUserResponseDto> getFollowers(String userId, Pageable pageable);
}

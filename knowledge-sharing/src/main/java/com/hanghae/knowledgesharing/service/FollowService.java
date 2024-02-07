package com.hanghae.knowledgesharing.service;

import com.hanghae.knowledgesharing.dto.response.follow.CheckFollowResponseDto;
import org.springframework.http.ResponseEntity;

public interface FollowService {
    ResponseEntity<CheckFollowResponseDto> checkFollow(String followerUserId, String followingUserId );
}

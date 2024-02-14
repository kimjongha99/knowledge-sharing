package com.hanghae.knowledgesharing.follow.serivce;

import com.hanghae.knowledgesharing.follow.dto.response.follow.CheckFollowResponseDto;
import org.springframework.http.ResponseEntity;

public interface FollowService {
    ResponseEntity<CheckFollowResponseDto> checkFollow(String followerUserId, String followingUserId );
}

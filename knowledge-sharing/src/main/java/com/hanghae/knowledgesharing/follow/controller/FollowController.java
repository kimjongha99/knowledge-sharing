package com.hanghae.knowledgesharing.follow.controller;


import com.hanghae.knowledgesharing.follow.dto.response.CheckFollowResponseDto;
import com.hanghae.knowledgesharing.follow.dto.response.FollowingUserResponseDto;
import com.hanghae.knowledgesharing.follow.serivce.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/follows")
public class FollowController {

    private  final FollowService followService;

    @PostMapping("/{followId}/{followingId}")
    ResponseEntity<CheckFollowResponseDto> checkFollow(
            @PathVariable String followId,
            @PathVariable String followingId
            ) {
        ResponseEntity<CheckFollowResponseDto> response = followService.checkFollow(followId, followingId);
        return response;

    }

    /*
      userId 가 팔로우하고있는사람 목록
     */

    @GetMapping("/following/{userId}")
    public ResponseEntity<Page<FollowingUserResponseDto>> getFollowingUsers(
            @PathVariable String userId, Pageable pageable
    ) {
        Page<FollowingUserResponseDto> followingUsersPage = followService.getFollowingUsers(userId, pageable);
        return ResponseEntity.ok(followingUsersPage);
    }


}

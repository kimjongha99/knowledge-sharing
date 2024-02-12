package com.hanghae.knowledgesharing.controller;


import com.hanghae.knowledgesharing.dto.response.follow.CheckFollowResponseDto;
import com.hanghae.knowledgesharing.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

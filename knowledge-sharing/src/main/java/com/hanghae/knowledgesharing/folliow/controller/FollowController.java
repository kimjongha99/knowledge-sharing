package com.hanghae.knowledgesharing.folliow.controller;


import com.hanghae.knowledgesharing.common.dto.ResponseDto;
import com.hanghae.knowledgesharing.folliow.dto.response.CheckFollowResponseDto;
import com.hanghae.knowledgesharing.folliow.dto.response.FollowerUserResponseDto;
import com.hanghae.knowledgesharing.folliow.dto.response.FollowingUserResponseDto;
import com.hanghae.knowledgesharing.folliow.sevice.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/follows")
public class FollowController {

    private  final FollowService followService;


    @PostMapping("/{followId}/{followingId}")
    public ResponseDto<CheckFollowResponseDto>checkFollow(
            @PathVariable String followId,
            @PathVariable String followingId
    ){
        CheckFollowResponseDto responseDto = followService.checkFollow(followId, followingId);
        return ResponseDto.success(responseDto);
    }



    /*
      userId 가 팔로우하고있는사람 목록
     */

    @GetMapping("/{userId}/following")
    public ResponseDto<Page<FollowingUserResponseDto>> getFollowingUsers(
            @PathVariable String userId, Pageable pageable
    ) {
        Page<FollowingUserResponseDto>  followingUsersPage = followService.getFollowingUsers(userId, pageable);
        return ResponseDto.success(followingUsersPage);
    }

 /*
      userId 를 팔로우하고있는사람 목록
     */
 @GetMapping("/{userId}/followers")
 public ResponseDto<Page<FollowerUserResponseDto>> getFollowers(
         @PathVariable String userId, Pageable pageable) {
     Page<FollowerUserResponseDto> followersPage = followService.getFollowers(userId, pageable);
     return ResponseDto.success(followersPage);
 }

}

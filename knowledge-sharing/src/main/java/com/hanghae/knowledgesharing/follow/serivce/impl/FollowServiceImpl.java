package com.hanghae.knowledgesharing.follow.serivce.impl;

import com.hanghae.knowledgesharing.common.entity.Follow;
import com.hanghae.knowledgesharing.common.entity.User;
import com.hanghae.knowledgesharing.common.enums.CheckFollowEnum;
import com.hanghae.knowledgesharing.follow.dto.response.CheckFollowResponseDto;
import com.hanghae.knowledgesharing.follow.dto.response.FollowingUserResponseDto;
import com.hanghae.knowledgesharing.follow.repository.FollowRepository;
import com.hanghae.knowledgesharing.follow.serivce.FollowService;
import com.hanghae.knowledgesharing.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private  final UserRepository userRepository;
    private  final FollowRepository followRepository;

    @Override
    public ResponseEntity<CheckFollowResponseDto> checkFollow(String followerUserId, String followingUserId) {
        try {
            //팔로우 유저와 팔로잉 유저 의 존재 확인 및 객체 가져오기
            User follower  = userRepository.findByUserId(followerUserId);
            if (follower  == null) return CheckFollowResponseDto.notExistUser();
            User following  = userRepository.findByUserId(followingUserId);
            if (following  == null) return CheckFollowResponseDto.notExistUser();


            Optional<Follow> followRelation = followRepository.findByFromUserAndToUser(follower, following);

            if(followRelation.isPresent()){
                followRepository.delete(followRelation.get());
                return CheckFollowResponseDto.success(CheckFollowEnum.unFollowing);

            }else {
                Follow newFollow = new Follow(following, follower);
                followRepository.save(newFollow);
                return CheckFollowResponseDto.success(CheckFollowEnum.following);
            }


        } catch (Exception e) {
            e.printStackTrace();
            return CheckFollowResponseDto.databaseError();
        }

    }


    @Override
    public Page<FollowingUserResponseDto> getFollowingUsers(String userId, Pageable pageable) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            return Page.empty(pageable);
        }
        Page<Follow> followsPage = followRepository.findAllByFromUser(userOptional.get(), pageable);
        return followsPage.map(follow -> new FollowingUserResponseDto(
                follow.getToUser().getUserId(),
                follow.getToUser().getEmail()
        ));
    }

    @Override
    public Page<FollowingUserResponseDto> getFollowers(String userId, Pageable pageable) {
        return null;
    }

}

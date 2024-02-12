package com.hanghae.knowledgesharing.service.impl;

import com.hanghae.knowledgesharing.dto.response.follow.CheckFollowResponseDto;
import com.hanghae.knowledgesharing.entity.Follow;
import com.hanghae.knowledgesharing.entity.User;
import com.hanghae.knowledgesharing.enums.CheckFollowEnum;
import com.hanghae.knowledgesharing.repository.FollowRepository;
import com.hanghae.knowledgesharing.repository.UserRepository;
import com.hanghae.knowledgesharing.service.FollowService;
import lombok.RequiredArgsConstructor;
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
}

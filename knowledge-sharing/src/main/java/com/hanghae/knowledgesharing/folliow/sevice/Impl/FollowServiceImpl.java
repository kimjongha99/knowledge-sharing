package com.hanghae.knowledgesharing.folliow.sevice.Impl;

import com.hanghae.knowledgesharing.common.entity.Follow;
import com.hanghae.knowledgesharing.common.entity.User;
import com.hanghae.knowledgesharing.common.enums.CheckFollowEnum;
import com.hanghae.knowledgesharing.common.exception.CustomException;
import com.hanghae.knowledgesharing.common.exception.ErrorCode;
import com.hanghae.knowledgesharing.folliow.dto.response.CheckFollowResponseDto;
import com.hanghae.knowledgesharing.folliow.dto.response.FollowerUserResponseDto;
import com.hanghae.knowledgesharing.folliow.dto.response.FollowingUserResponseDto;
import com.hanghae.knowledgesharing.folliow.repository.FollowRepository;
import com.hanghae.knowledgesharing.folliow.sevice.FollowService;
import com.hanghae.knowledgesharing.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
    private  final UserRepository userRepository;
    private  final FollowRepository followRepository;

    @Override
    public CheckFollowResponseDto checkFollow(String followId, String followingId) {
        // 팔로워 유저와 팔로잉 유저의 존재 확인 및 객체 가져오기
        User follower = userRepository.findByUserId(followId);
        if (follower == null) {
            throw new CustomException(ErrorCode.UserNotFound);
        }

        User following = userRepository.findByUserId(followingId);
        if (following == null) {
            throw new CustomException(ErrorCode.UserNotFound);
        }

        // 팔로우 관계 확인
        Optional<Follow> followRelation = followRepository.findByFromUserAndToUser(follower, following);

        if (followRelation.isPresent()) {
            // 이미 팔로우 상태라면, 언팔로우 처리
            followRepository.delete(followRelation.get());
            return new CheckFollowResponseDto("Unfollowed successfully", CheckFollowEnum.unFollowing);
        } else {
            // 팔로우 관계가 없다면, 팔로우 처리
            Follow newFollow = new Follow(follower, following);
            followRepository.save(newFollow);
            return new CheckFollowResponseDto("Followed successfully", CheckFollowEnum.following);
        }    }

    @Override
    public Page<FollowingUserResponseDto> getFollowingUsers(String userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.UserNotFound));
        Page<Follow> followsPage = followRepository.findAllByFromUser(user, pageable);

        return followsPage.map(follow -> new FollowingUserResponseDto(
                follow.getToUser().getUserId(),
                follow.getToUser().getEmail()
        ));
    }

    @Override
    public Page<FollowerUserResponseDto> getFollowers(String userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.UserNotFound));

        Page<Follow> followersPage = followRepository.findAllByToUser(user, pageable);

        return followersPage.map(follow -> new FollowerUserResponseDto(
                follow.getFromUser().getUserId(),
                follow.getFromUser().getEmail()
        ));

    }
}

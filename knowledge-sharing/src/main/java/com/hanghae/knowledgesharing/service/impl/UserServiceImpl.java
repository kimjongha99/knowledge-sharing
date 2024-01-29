package com.hanghae.knowledgesharing.service.impl;

import com.hanghae.knowledgesharing.dto.request.user.PatchProfileImageRequestDto;
import com.hanghae.knowledgesharing.dto.response.ResponseDto;
import com.hanghae.knowledgesharing.dto.response.user.GetSignInUserResponseDto;
import com.hanghae.knowledgesharing.dto.response.user.PatchProfileImageResponseDto;
import com.hanghae.knowledgesharing.entity.User;
import com.hanghae.knowledgesharing.repository.UserRepository;
import com.hanghae.knowledgesharing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    @Override
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String userId) {

        User user= null;
        try {

            user = userRepository.findByUserId(userId);
            if(user == null) return GetSignInUserResponseDto.notExistUser();

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetSignInUserResponseDto.success(user);

    }

    @Override
    public ResponseEntity<? super PatchProfileImageResponseDto> patchProfileImage(PatchProfileImageRequestDto dto, String userId) {

        try {

            User user = userRepository.findByUserId(userId);
            if(user == null) return PatchProfileImageResponseDto.noExistUser();

            String profileImageUrl = dto.getProfileImageUrl();
            user.setProfileImageUrl(profileImageUrl);
            userRepository.save(user);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PatchProfileImageResponseDto.success();
    }


}

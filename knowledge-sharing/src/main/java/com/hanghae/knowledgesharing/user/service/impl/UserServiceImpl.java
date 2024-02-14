package com.hanghae.knowledgesharing.user.service.impl;

import com.hanghae.knowledgesharing.user.dto.request.user.PatchPasswordRequestDto;
import com.hanghae.knowledgesharing.user.dto.request.user.PatchProfileImageRequestDto;
import com.hanghae.knowledgesharing.common.dto.ResponseDto;
import com.hanghae.knowledgesharing.user.dto.response.user.GetSignInUserResponseDto;
import com.hanghae.knowledgesharing.user.dto.response.user.GetUserResponseDto;
import com.hanghae.knowledgesharing.user.dto.response.user.PatchPasswordResponseDto;
import com.hanghae.knowledgesharing.user.dto.response.user.PatchProfileImageResponseDto;
import com.hanghae.knowledgesharing.common.entity.User;
import com.hanghae.knowledgesharing.user.repository.UserRepository;
import com.hanghae.knowledgesharing.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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

    @Override
    public ResponseEntity<? super GetUserResponseDto> getUser(String userId) {
        User user = null;

        try {
            user = userRepository.findByUserId(userId);
            if(user==null) return GetUserResponseDto.noExistUser();
        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetUserResponseDto.success(user);
    }

    @Override
    public ResponseEntity<? super PatchPasswordResponseDto> patchPassword(PatchPasswordRequestDto dto, String userId) {
        try {
            User user = userRepository.findByUserId(userId);
            if(user == null) return PatchPasswordResponseDto.noExistUser();
            // 기존 비밀번호와 새 비밀번호를 비교합니다.
            if (passwordEncoder.matches(dto.getNewPassword(), user.getPassword())) {
                // 비밀번호가 동일하면 이전 비밀번호 오류 응답을 반환합니다.
                return PatchPasswordResponseDto.previousPassword();
            } else {
                // 비밀번호가 다른 경우 새 비밀번호를 인코딩하여 저장합니다.
                user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
                userRepository.save(user);


            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return  PatchPasswordResponseDto.success();
    }


}

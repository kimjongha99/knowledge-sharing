package com.hanghae.knowledgesharing.user.service;

import com.hanghae.knowledgesharing.user.dto.request.user.PatchPasswordRequestDto;
import com.hanghae.knowledgesharing.user.dto.request.user.PatchProfileImageRequestDto;
import com.hanghae.knowledgesharing.user.dto.response.user.GetSignInUserResponseDto;
import com.hanghae.knowledgesharing.user.dto.response.user.GetUserResponseDto;
import com.hanghae.knowledgesharing.user.dto.response.user.PatchPasswordResponseDto;
import com.hanghae.knowledgesharing.user.dto.response.user.PatchProfileImageResponseDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String userId);

    ResponseEntity<? super PatchProfileImageResponseDto> patchProfileImage(PatchProfileImageRequestDto requestBody, String email);

    ResponseEntity<? super GetUserResponseDto> getUser(String userId);

    ResponseEntity<? super PatchPasswordResponseDto> patchPassword(PatchPasswordRequestDto requestBody, String userId);
}

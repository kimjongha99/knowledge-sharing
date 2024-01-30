package com.hanghae.knowledgesharing.service;

import com.hanghae.knowledgesharing.dto.request.user.PatchPasswordRequestDto;
import com.hanghae.knowledgesharing.dto.request.user.PatchProfileImageRequestDto;
import com.hanghae.knowledgesharing.dto.response.user.GetSignInUserResponseDto;
import com.hanghae.knowledgesharing.dto.response.user.GetUserResponseDto;
import com.hanghae.knowledgesharing.dto.response.user.PatchPasswordResponseDto;
import com.hanghae.knowledgesharing.dto.response.user.PatchProfileImageResponseDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String userId);

    ResponseEntity<? super PatchProfileImageResponseDto> patchProfileImage(PatchProfileImageRequestDto requestBody, String email);

    ResponseEntity<? super GetUserResponseDto> getUser(String userId);

    ResponseEntity<? super PatchPasswordResponseDto> patchPassword(PatchPasswordRequestDto requestBody, String userId);
}

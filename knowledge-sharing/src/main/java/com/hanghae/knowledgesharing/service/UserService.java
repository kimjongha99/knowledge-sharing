package com.hanghae.knowledgesharing.service;

import com.hanghae.knowledgesharing.dto.request.user.PatchProfileImageRequestDto;
import com.hanghae.knowledgesharing.dto.response.user.GetSignInUserResponseDto;
import com.hanghae.knowledgesharing.dto.response.user.PatchProfileImageResponseDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String userId);

    ResponseEntity<? super PatchProfileImageResponseDto> patchProfileImage(PatchProfileImageRequestDto requestBody, String email);
}

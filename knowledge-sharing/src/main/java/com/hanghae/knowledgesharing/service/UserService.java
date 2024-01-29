package com.hanghae.knowledgesharing.service;

import com.hanghae.knowledgesharing.dto.response.user.GetSignInUserResponseDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String userId);
}

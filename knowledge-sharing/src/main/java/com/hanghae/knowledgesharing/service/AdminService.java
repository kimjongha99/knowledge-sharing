package com.hanghae.knowledgesharing.service;

import com.hanghae.knowledgesharing.dto.response.admin.UserListResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface AdminService {

    ResponseEntity<UserListResponseDto> getAllUsers(String userId, String email, Pageable pageable);

}

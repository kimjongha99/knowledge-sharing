package com.hanghae.knowledgesharing.service;

import com.hanghae.knowledgesharing.dto.request.admin.UserAdminDeleteRequestDto;
import com.hanghae.knowledgesharing.dto.request.admin.UserRoleChangeRequestDto;
import com.hanghae.knowledgesharing.dto.response.admin.UserAdminDeleteResponseDto;
import com.hanghae.knowledgesharing.dto.response.admin.UserListResponseDto;
import com.hanghae.knowledgesharing.dto.response.admin.UserRoleChangeResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface AdminService {

    ResponseEntity<UserListResponseDto> getAllUsers(String userId, String email, Pageable pageable);

    ResponseEntity<UserRoleChangeResponseDto> userRoleChanges(UserRoleChangeRequestDto requestDto);

    ResponseEntity<UserAdminDeleteResponseDto> userAdminDelete(UserAdminDeleteRequestDto requestDto);
}

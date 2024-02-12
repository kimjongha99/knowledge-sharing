package com.hanghae.knowledgesharing.service.impl;

import com.hanghae.knowledgesharing.dto.request.admin.UserAdminDeleteRequestDto;
import com.hanghae.knowledgesharing.dto.request.admin.UserRoleChangeRequestDto;
import com.hanghae.knowledgesharing.dto.response.admin.UserAdminDeleteResponseDto;
import com.hanghae.knowledgesharing.dto.response.admin.UserDetailDto;
import com.hanghae.knowledgesharing.dto.response.admin.UserListResponseDto;
import com.hanghae.knowledgesharing.dto.response.admin.UserRoleChangeResponseDto;
import com.hanghae.knowledgesharing.entity.User;
import com.hanghae.knowledgesharing.repository.UserRepository;
import com.hanghae.knowledgesharing.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<UserListResponseDto> getAllUsers(String userId, String email, Pageable pageable) {

        Page<User> usersPage;
        if (userId != null && !userId.isEmpty()) {
            usersPage = userRepository.findByUserIdContaining(userId, pageable);
        } else if (email != null && !email.isEmpty()) {
            usersPage = userRepository.findByEmailContaining(email, pageable);
        } else {
            usersPage = userRepository.findAll(pageable);
        }
        Page<UserDetailDto> userDetailPage = usersPage.map(user -> new UserDetailDto(
                user.getUserId(),
                user.getEmail(),
                user.getRole().getAuthority(),
                user.getType()
        ));

        return UserListResponseDto.success(userDetailPage);

    }

    @Override
    public ResponseEntity<UserRoleChangeResponseDto> userRoleChanges(UserRoleChangeRequestDto requestDto) {
        try {
            int updatedRows = userRepository.updateUserRole(requestDto.getChangeUserId(), requestDto.getRoleEnum());
            if (updatedRows == 0) {
                // 업데이트된 행이 없습니다. 이는 사용자를 찾을 수 없음을 나타냅니다.
                return UserRoleChangeResponseDto.noExistUser();
            }

            return UserRoleChangeResponseDto.success();

        } catch (Exception e) {
            e.printStackTrace();
            return UserRoleChangeResponseDto.databaseError();
        }



    }

    @Override
    public ResponseEntity<UserAdminDeleteResponseDto> userAdminDelete(UserAdminDeleteRequestDto requestDto) {
        try {
            userRepository.deleteById(requestDto.getDeleteUserId());
            return UserAdminDeleteResponseDto.success();
        } catch (Exception e) {
            e.printStackTrace();
            return  UserAdminDeleteResponseDto.databaseError();
        }

    }
}
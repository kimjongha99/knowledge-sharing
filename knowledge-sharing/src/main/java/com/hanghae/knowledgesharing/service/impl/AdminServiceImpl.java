package com.hanghae.knowledgesharing.service.impl;

import com.hanghae.knowledgesharing.dto.response.admin.UserDetailDto;
import com.hanghae.knowledgesharing.dto.response.admin.UserListResponseDto;
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
}
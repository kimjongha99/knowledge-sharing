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
    public ResponseEntity<UserListResponseDto> getAllUsers(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);

        Page<UserDetailDto> userDetailPage = usersPage.map(user -> new UserDetailDto(
                user.getUserId(),
                user.getEmail(),
                user.getRole().getAuthority(),
                user.getType() // Assuming getType() returns a string representation of the user type
        ));

        return UserListResponseDto.success(userDetailPage);
    }
}

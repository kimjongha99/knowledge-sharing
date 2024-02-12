package com.hanghae.knowledgesharing.controller;


import com.hanghae.knowledgesharing.dto.request.admin.UserAdminDeleteRequestDto;
import com.hanghae.knowledgesharing.dto.request.admin.UserRoleChangeRequestDto;
import com.hanghae.knowledgesharing.dto.response.admin.UserAdminDeleteResponseDto;
import com.hanghae.knowledgesharing.dto.response.admin.UserListResponseDto;
import com.hanghae.knowledgesharing.dto.response.admin.UserRoleChangeResponseDto;
import com.hanghae.knowledgesharing.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;


    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String authorities = authentication.getAuthorities().stream()
                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                    .collect(Collectors.joining(", "));

            String email = authentication.getName(); // Assuming the username is the email
            return ResponseEntity.ok("admin Service is Healthy, Authenticated user: " + email + ", Authorities: " + authorities);
        }

        return ResponseEntity.ok("Authentication is required.");
    }


    /*
    유저 전체 목록 조회 및 페이징 처리

    http://localhost:8080/users?page=0&size=10
    http://localhost:8080/users?userId=testUserId&page=0&size=10
    http://localhost:8080/users?email=test@example.com&page=0&size=10

     */
    @GetMapping("/users")
    public ResponseEntity<UserListResponseDto> getAllUsers(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String email,
            @PageableDefault Pageable pageable
    ) {
        ResponseEntity<UserListResponseDto> response = adminService.getAllUsers(userId, email, pageable);
        return response;
    }


    @PostMapping("/change-role")
    public ResponseEntity<UserRoleChangeResponseDto> userRoleChanges(
            @RequestBody UserRoleChangeRequestDto requestDto
    ) {
        ResponseEntity<UserRoleChangeResponseDto> response = adminService.userRoleChanges(requestDto);

        return response;
    }


    @PostMapping("/users-delete")
    public ResponseEntity<UserAdminDeleteResponseDto> userAdminDelete(
            @RequestBody UserAdminDeleteRequestDto requestDto
    ){
        ResponseEntity<UserAdminDeleteResponseDto> response = adminService.userAdminDelete(requestDto);
        return response;
    }
}

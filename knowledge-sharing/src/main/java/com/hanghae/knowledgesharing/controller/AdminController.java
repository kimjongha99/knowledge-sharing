package com.hanghae.knowledgesharing.controller;


import com.hanghae.knowledgesharing.dto.response.admin.UserListResponseDto;
import com.hanghae.knowledgesharing.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

       private  final AdminService adminService;


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
         */
        @GetMapping("/users")
        public ResponseEntity<UserListResponseDto> getAllUsers(
                @PageableDefault() Pageable pageable

        ) {
            ResponseEntity<UserListResponseDto> response = adminService.getAllUsers(pageable);
            return response;
        }



}

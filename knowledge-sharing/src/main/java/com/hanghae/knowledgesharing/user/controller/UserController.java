package com.hanghae.knowledgesharing.user.controller;


import com.hanghae.knowledgesharing.user.dto.request.user.PatchPasswordRequestDto;
import com.hanghae.knowledgesharing.user.dto.request.user.PatchProfileImageRequestDto;
import com.hanghae.knowledgesharing.user.dto.response.user.GetSignInUserResponseDto;
import com.hanghae.knowledgesharing.user.dto.response.user.GetUserResponseDto;
import com.hanghae.knowledgesharing.user.dto.response.user.PatchPasswordResponseDto;
import com.hanghae.knowledgesharing.user.dto.response.user.PatchProfileImageResponseDto;
import com.hanghae.knowledgesharing.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String authorities = authentication.getAuthorities().stream()
                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                    .collect(Collectors.joining(", "));

            String email = authentication.getName(); // Assuming the username is the email
            return ResponseEntity.ok("Member Service is Healthy, Authenticated user: " + email + ", Authorities: " + authorities);
        }

        return ResponseEntity.ok("Authentication is required.");
    }

    @GetMapping("")
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(
            @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<? super GetSignInUserResponseDto> response = userService.getSignInUser(userId);
        return response;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<? super GetUserResponseDto> getUser(
            @PathVariable("userId") String userId
    ) {
        ResponseEntity<? super GetUserResponseDto> response = userService.getUser(userId);
        return response;
    }


    @PatchMapping("/profile-image")
    public ResponseEntity<? super PatchProfileImageResponseDto> patchProfileImage(
            @RequestBody @Valid PatchProfileImageRequestDto requestBody,
            @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<? super PatchProfileImageResponseDto> response = userService.patchProfileImage(requestBody, userId);
        return response;
    }

    @PatchMapping("/password")
    public ResponseEntity<? super PatchPasswordResponseDto>  patchPassword(
            @RequestBody @Valid PatchPasswordRequestDto requestBody,
            @AuthenticationPrincipal String userId
    ){
        ResponseEntity<? super PatchPasswordResponseDto> response = userService.patchPassword(requestBody,userId);
        return response;
    }
}

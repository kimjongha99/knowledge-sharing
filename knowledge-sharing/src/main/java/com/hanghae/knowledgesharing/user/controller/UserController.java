package com.hanghae.knowledgesharing.user.controller;


import com.hanghae.knowledgesharing.common.dto.ResponseDto;
import com.hanghae.knowledgesharing.user.dto.request.PatchPasswordRequestDto;
import com.hanghae.knowledgesharing.user.dto.request.PatchProfileImageRequestDto;
import com.hanghae.knowledgesharing.user.dto.response.GetSignInUserResponseDto;
import com.hanghae.knowledgesharing.user.dto.response.GetUserResponseDto;
import com.hanghae.knowledgesharing.user.dto.response.UserArticleResponseDto;
import com.hanghae.knowledgesharing.user.sevice.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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


    @Operation(summary = "사용자 정보를 조회", description = "현재 로그인한 사용자 정보를 조회 합니다.")
    @GetMapping("")
    public ResponseDto<GetSignInUserResponseDto> getSignInUser(@AuthenticationPrincipal String userId) {
        GetSignInUserResponseDto result = userService.getSignInUser(userId);
        return ResponseDto.success(result) ;
    }


    // 사용자 정보를 조회합니다.
    @Operation(summary = "사용자 정보를 조회", description = "현재  사용자 정보를 조회 합니다.")
    @GetMapping("/{userId}")
    public ResponseDto<GetUserResponseDto> getUser(@PathVariable("userId") String userId) {

        GetUserResponseDto result = userService.getUser(userId);
        return ResponseDto.success(result) ;
    }

    @Operation(summary = "프로필이미지", description = "프사 변경 합니다..")
    @PatchMapping("/profile-image")
    public ResponseDto<String> patchProfileImage(
            @RequestBody @Valid PatchProfileImageRequestDto requestBody,
            @AuthenticationPrincipal String userId) {
        String result = userService.patchProfileImage(requestBody, userId);
        return ResponseDto.success(result) ;
    }

    @Operation(summary = "비밀번호 변경", description = "비밀번호 변경 합니다..")
    @PatchMapping("/password")
    public ResponseDto<String> patchPassword(
            @RequestBody @Valid PatchPasswordRequestDto requestBody,
            @AuthenticationPrincipal String userId
    ) {
        String result = userService.patchPassword(requestBody, userId);
        return ResponseDto.success(result) ;
    }


    @Operation(summary = "내가 쓴글 조회", description = "내가 쓴글 조회.")
    @GetMapping("/myArticles")
    public ResponseDto<UserArticleResponseDto> getUserArticles(
            @AuthenticationPrincipal String  userId,
            @PageableDefault Pageable pageable){

        UserArticleResponseDto response = userService.getUserArticles(userId,pageable );
        return  ResponseDto.success(response);
    }



}

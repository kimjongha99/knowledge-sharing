package com.hanghae.knowledgesharing.admin.controller;

import com.hanghae.knowledgesharing.admin.dto.request.UserAdminDeleteRequestDto;
import com.hanghae.knowledgesharing.admin.dto.request.UserRoleChangeRequestDto;
import com.hanghae.knowledgesharing.admin.dto.response.AdminArticleListResponseDto;
import com.hanghae.knowledgesharing.admin.dto.response.UserAdminDeleteResponseDto;
import com.hanghae.knowledgesharing.admin.dto.response.UserListResponseDto;
import com.hanghae.knowledgesharing.admin.dto.response.UserRoleChangeResponseDto;
import com.hanghae.knowledgesharing.admin.sevice.AdminService;
import com.hanghae.knowledgesharing.article.dto.response.DeleteArticleResponseDto;
import com.hanghae.knowledgesharing.article.dto.response.GetArticleResponseDto;
import com.hanghae.knowledgesharing.common.dto.ResponseDto;
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
        public ResponseDto<UserListResponseDto> getAllUsers(
                @RequestParam(required = false) String userId,
                @RequestParam(required = false) String email,
                @PageableDefault Pageable pageable
        ){
                UserListResponseDto response = adminService.getAllUsers(userId, email, pageable);
                return  ResponseDto.success(response);
        }

        @GetMapping("/articles")
        public ResponseDto<AdminArticleListResponseDto> getAllArticles(
                @RequestParam(required = false) String title,
                @RequestParam(required = false) String content,
                @RequestParam(required = false) String hashtag,
                @PageableDefault Pageable pageable
        ){
                AdminArticleListResponseDto response = adminService.getAllArticles(title, content,hashtag, pageable);
                return  ResponseDto.success(response);
        }

        @PostMapping("/change-role")
        public ResponseDto<UserRoleChangeResponseDto> userRoleChanges(
                @RequestBody UserRoleChangeRequestDto requestDto
        ) {
                UserRoleChangeResponseDto response = adminService.userRoleChanges(requestDto);
                return ResponseDto.success(response);
        }



        @DeleteMapping("/users-delete")
        public ResponseDto<UserAdminDeleteResponseDto> userAdminDelete(
                @RequestBody UserAdminDeleteRequestDto requestDto
        ){
                UserAdminDeleteResponseDto response = adminService.userAdminDelete(requestDto);
                return ResponseDto.success(response);
        }



        @DeleteMapping("/admin/articles/{articleId}")
        public ResponseDto<DeleteArticleResponseDto> deleteArticle(
                @PathVariable("articleId") Long articleId
        ) {
                 DeleteArticleResponseDto response = adminService.deleteArticle(articleId);
                return ResponseDto.success(response);

        }

        @GetMapping("/admin/articles/{articleId}")
        public ResponseDto<GetArticleResponseDto> getArticle(
                @PathVariable("articleId") Long articleId
        ) {
                GetArticleResponseDto response = adminService.getArticle(articleId);

                return ResponseDto.success(response);
        }












}

//package com.hanghae.knowledgesharing.service;
//
//import com.hanghae.knowledgesharing.dto.request.user.PatchPasswordRequestDto;
//import com.hanghae.knowledgesharing.dto.response.user.GetSignInUserResponseDto;
//import com.hanghae.knowledgesharing.dto.response.user.PatchPasswordResponseDto;
//import com.hanghae.knowledgesharing.entity.User;
//import com.hanghae.knowledgesharing.enums.UserRoleEnum;
//import com.hanghae.knowledgesharing.repository.UserRepository;
//import com.hanghae.knowledgesharing.service.impl.UserServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//import static org.mockito.MockitoAnnotations.openMocks;
//
//@DisplayName("유저서비스 테스트")
//class UserServiceTest {
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @InjectMocks
//    private UserServiceImpl userService;
//
//    @BeforeEach
//    void setUp() {
//        openMocks(this);
//    }
//
//    @Test
//    @DisplayName("로그인시 유저정보 -성공테스트")
//    void getSignInUser_Success() {
//
//        // given
//        String existingUserId = "existing_user_id";
//        User user = new User(existingUserId, "password", "user@example.com", "app", UserRoleEnum.USER, "image_url", null); // 모든 필드를 초기화하는 생성자를 사용
//
//        when(userRepository.findByUserId(existingUserId)).thenReturn(user);
//
//        // when
//        ResponseEntity<? super GetSignInUserResponseDto> response = userService.getSignInUser(existingUserId);
//        // then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        GetSignInUserResponseDto responseBody = (GetSignInUserResponseDto) response.getBody();
//        assertEquals(existingUserId, responseBody.getUserId());
//        assertEquals("user@example.com", responseBody.getEmail());
//        assertEquals("image_url", responseBody.getProfileImageUrl());
//        assertEquals(UserRoleEnum.USER, responseBody.getRole());
//    }
//
//
//    @Test
//    @DisplayName("Success - 비밀번호 변경")
//    void patchPassword_Success() {
//        // given
//        String userId = "existing_user_id";
//        String newPassword = "newPassword";
//        User user = new User(userId, "oldEncodedPassword", "user@example.com", "app", UserRoleEnum.USER, "image_url", null);
//
//        PatchPasswordRequestDto requestDto = new PatchPasswordRequestDto();
//        requestDto.setNewPassword(newPassword);
//
//        when(userRepository.findByUserId(userId)).thenReturn(user);
//        when(passwordEncoder.matches(newPassword, user.getPassword())).thenReturn(false);
//        when(passwordEncoder.encode(newPassword)).thenReturn("newEncodedPassword");
//
//        // when
//        ResponseEntity<? super PatchPasswordResponseDto> response = userService.patchPassword(requestDto, userId);
//
//        // then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        verify(userRepository, times(1)).save(user);
//    }
//
//
//}
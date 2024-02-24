package com.hanghae.knowledgesharing.user.sevice;

import com.hanghae.knowledgesharing.auth.dto.request.auth.SignUpRequestDto;
import com.hanghae.knowledgesharing.common.entity.User;
import com.hanghae.knowledgesharing.user.repository.UserRepository;
import com.hanghae.knowledgesharing.user.sevice.Impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;


    // 여기에 필요한 모의 객체를 선언할 수 있습니다.
    private User mockUser;


    @BeforeEach
    void setUp() {
        // SignUpRequestDto를 사용하여 테스트에 사용될 가짜 User 객체를 초기화합니다.
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
        signUpRequestDto.setId("testUser");
        signUpRequestDto.setPassword("password"); // 비밀번호는 실제 테스트에서는 인코딩 과정을 거치게 됩니다.
        signUpRequestDto.setEmail("test@example.com");

        // User 객체 생성
        mockUser = new User(signUpRequestDto);
    }

    @Test
    @DisplayName("Success - 비밀번호 변경")
    void getSignInUser() {
ddd

    }






}
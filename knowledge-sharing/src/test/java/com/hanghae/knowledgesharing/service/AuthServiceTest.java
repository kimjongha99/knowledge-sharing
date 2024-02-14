package com.hanghae.knowledgesharing.service;

import com.hanghae.knowledgesharing.auth.dto.request.IdCheckRequestDto;
import com.hanghae.knowledgesharing.auth.dto.request.SignUpRequestDto;
import com.hanghae.knowledgesharing.auth.dto.response.auth.IdCheckResponseDto;
import com.hanghae.knowledgesharing.auth.dto.response.auth.SignUpResponseDto;
import com.hanghae.knowledgesharing.common.entity.Certification;
import com.hanghae.knowledgesharing.common.entity.User;
import com.hanghae.knowledgesharing.common.util.email.EmailProvider;
import com.hanghae.knowledgesharing.common.jwt.provider.JwtProvider;
import com.hanghae.knowledgesharing.auth.repository.CertificationRepository;
import com.hanghae.knowledgesharing.user.repository.UserRepository;
import com.hanghae.knowledgesharing.auth.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@DisplayName("인가 테스트")
class AuthServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private CertificationRepository certificationRepository;
    @Mock
    private JwtProvider jwtProvider;
    @Mock
    private EmailProvider emailProvider;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }


    @Test
    @DisplayName("이메일 중복 성공 테스트")
    void emailExist() {
        // given
        String uniqueUserId = "unique_user_id";
        IdCheckRequestDto requestDto = new IdCheckRequestDto();
        requestDto.setId(uniqueUserId);
        when(userRepository.existsByUserId(uniqueUserId)).thenReturn(false);
        // when
        ResponseEntity<? super IdCheckResponseDto> response = authService.idCheck(requestDto);
        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof IdCheckResponseDto);
    }

    @Test
    @DisplayName("이메일 중복 실패 테스트")
    void idCheck_DuplicateID() {
        // given
        String duplicateUserId = "duplicate_user_id"; // 테스트할 중복된 사용자 ID를 설정합니다.
        IdCheckRequestDto requestDto = new IdCheckRequestDto(); // ID 확인을 위한 요청 DTO를 생성합니다.
        requestDto.setId(duplicateUserId); // 요청 DTO에 중복된 사용자 ID를 설정합니다.
        when(userRepository.existsByUserId(duplicateUserId)).thenReturn(true); // userRepository의 existsByUserId 메서드가 해당 ID로 호출될 때 true를 반환하도록 설정합니다. 즉, 사용자 ID가 중복되었다고 가정합니다.
        // when
        ResponseEntity<? super IdCheckResponseDto> response = authService.idCheck(requestDto); // AuthService의 idCheck 메서드를 호출하고 결과를 response 변수에 저장합니다.

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()); // 응답의 상태 코드가 BAD_REQUEST(400)인지 확인합니다.
    }


    @Test
    @DisplayName("회원가입 성공테스트")
    void signUp_Success() {
        // given
        SignUpRequestDto requestDto = new SignUpRequestDto();
        // 샘플 데이터로 requestDto를 채웁니다.
        requestDto.setId("new_user_id");
        requestDto.setPassword("password");
        requestDto.setEmail("test@example.com");
        requestDto.setCertificationNumber("certification123");


        Certification validCertification = new Certification("new_user_id", "test@example.com", "certification123");

        when(userRepository.existsByUserId(requestDto.getId())).thenReturn(false);
        when(certificationRepository.findByUserId(requestDto.getId())).thenReturn(validCertification);
        when(passwordEncoder.encode(requestDto.getPassword())).thenReturn("encodedPassword");

        // when
        ResponseEntity<? super SignUpResponseDto> response = authService.signUp(requestDto);
        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userRepository, times(1)).save(any(User.class)); //이 코드는 userRepository의 save 메서드가 정확히 한 번 호출되었는지를 검증합니다.


    }

    @Test
    @DisplayName("중복아이디로 인한 실패 테스트")
    void signUp_DuplicateUserId() {
        // given
        SignUpRequestDto requestDto = new SignUpRequestDto();
        requestDto.setId("new_user_id");
        requestDto.setPassword("password");
        requestDto.setEmail("test@example.com");
        requestDto.setCertificationNumber("certification123");

        when(userRepository.existsByUserId(anyString())).thenReturn(true);
        // when
        ResponseEntity<? super SignUpResponseDto> response = authService.signUp(requestDto);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(userRepository, never()).save(any(User.class));  //이 코드는 userRepository의 save 메서드가 한 번도 호출되지 않았는지를 검증합니다  never()는 메서드가 호출되지 않았음을 의미합니다.

    }





}


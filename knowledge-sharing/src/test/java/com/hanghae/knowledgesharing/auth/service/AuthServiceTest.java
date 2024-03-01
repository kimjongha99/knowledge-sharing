package com.hanghae.knowledgesharing.auth.service;

import com.hanghae.knowledgesharing.auth.dto.request.auth.CheckCertificationRequestDto;
import com.hanghae.knowledgesharing.auth.dto.request.auth.EmailCertificationRequestDto;
import com.hanghae.knowledgesharing.auth.dto.request.auth.IdCheckRequestDto;
import com.hanghae.knowledgesharing.auth.repository.CertificationRepository;
import com.hanghae.knowledgesharing.auth.service.impl.AuthServiceImpl;
import com.hanghae.knowledgesharing.common.entity.Certification;
import com.hanghae.knowledgesharing.common.exception.CustomException;
import com.hanghae.knowledgesharing.common.exception.ErrorCode;
import com.hanghae.knowledgesharing.common.util.email.EmailProvider;
import com.hanghae.knowledgesharing.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private CertificationRepository certificationRepository;

    @Mock
    private EmailProvider emailProvider;

    @InjectMocks
    private AuthServiceImpl authService;

    private IdCheckRequestDto requestDto;

    @BeforeEach
    void setUp() {
        // 테스트에 사용될 IdCheckRequestDto 객체를 초기화합니다.
        requestDto = new IdCheckRequestDto("testUser");

    }

    @Test
    @DisplayName("실패 테스트 - 기존 사용자 ID가 주어졌을 때 ID 확인 시 CustomException 발생")
    void whenIdExists_thenThrowCustomException() {
        // Given: 설정 - 중복된 사용자 ID가 존재한다고 가정
        when(userRepository.existsByUserId("testUser")).thenReturn(true);

        // When & Then: 동작 실행 및 결과 검증 - CustomException이 발생하는지 확인
        CustomException thrown = assertThrows(CustomException.class, () -> authService.idChecking(requestDto));
        // 예외 타입이 올바른지 확인
        assertEquals(ErrorCode.ExistId, thrown.getErrorCode());
    }

    @Test
    @DisplayName("성공 테스트 - 존재하지 않는 사용자 ID가 있는 경우 ID 확인 시 성공 메시지 반환")
    void whenIdDoesNotExists_thenReturnSuccessMessage() {
        // Given: 설정 - 중복된 사용자 ID가 존재하지 않는다고 가정
        when(userRepository.existsByUserId("testUser")).thenReturn(false);

        // When: 동작 실행 - ID 중복 검사를 수행
        String result = authService.idChecking(requestDto);

        // Then: 결과 검증 - 성공 메시지가 반환되는지 확인
        assertEquals("중복된 아이디가 없습니다.", result);
    }


    @Test
    @DisplayName("실패 테스트 -기존 사용자 ID가 주어지면 이메일 인증 요청 시 기존 ID에 대해 CustomException을 발생시킵니다.")
    void whenUserIdExists_thenThrowException() {
        EmailCertificationRequestDto requestDto = new EmailCertificationRequestDto();
        requestDto.setId("test00");
        requestDto.setEmail("test00@email.com");

        when(userRepository.existsByUserId(requestDto.getId())).thenReturn(true);

        // When & Then: 동작 실행 및 결과 검증 - CustomException이 발생하는지 확인
        CustomException thrown = assertThrows(CustomException.class, () -> authService.emailCertification(requestDto));
        // 예외 타입이 올바른지 확인
        assertEquals(ErrorCode.ExistId, thrown.getErrorCode());

    }

    @Test
    @DisplayName("실패 테스트 - 이메일 전송 실패 시 이메일 인증 요청 시 메일 전송 실패에 대한 CustomException 발생")
    void whenEmailSendFails_thenThrowException() {
        EmailCertificationRequestDto requestDto = new EmailCertificationRequestDto();
        requestDto.setId("test00");
        requestDto.setEmail("test00@email.com");

        when(userRepository.existsByUserId(requestDto.getId())).thenReturn(false);
        when(emailProvider.sendCertificationMail(anyString(), anyString())).thenReturn(false);

        // When & Then: 동작 실행 및 결과 검증 - CustomException이 발생하는지 확인
        CustomException thrown = assertThrows(CustomException.class, () -> authService.emailCertification(requestDto));
        // 예외 타입이 올바른지 확인
        assertEquals(ErrorCode.MailSendFail, thrown.getErrorCode());

    }

    @Test
    @DisplayName("성공 테스트 - 이메일 전송 성공 시 이메일 인증 요청 시 성공 메시지 반환")
    void whenEmailSendSucceeds_thenReturnSuccessMessage() {
        EmailCertificationRequestDto requestDto = new EmailCertificationRequestDto();
        requestDto.setId("test00");
        requestDto.setEmail("test00@email.com");
        when(userRepository.existsByUserId(requestDto.getId())).thenReturn(false);
        when(emailProvider.sendCertificationMail(anyString(), anyString())).thenReturn(true);
        // When
        String result = authService.emailCertification(requestDto);
        // Then
        assertEquals("메일전송 성공했습니다.", result);
        verify(certificationRepository, times(1)).save(any(Certification.class));
    }




    @Test
    @DisplayName("존재하지 않는 인증이 있는 경우 인증을 확인할 때 CertificationNotFound 예외가 발생합니다.")
    void whenCertificationNotFound_thenThrowException() {
        CheckCertificationRequestDto  requestDto = new CheckCertificationRequestDto();
        requestDto.setId("test00");
        requestDto.setEmail("test00@email.com");
        requestDto.setCertificationNumber("12345");



        // Given
        when(certificationRepository.findByUserId(requestDto.getId())).thenReturn(null);

        // When & Then
        CustomException thrown=  assertThrows(CustomException.class, () -> authService.emailCertificationCheck(requestDto));
        assertEquals(ErrorCode.CertificationNotFound, thrown.getErrorCode());

    }

    @Test
    @DisplayName("일치하지 않는 인증 정보가 있는 경우 인증을 확인할 때 CertificationMismatch 예외가 발생합니다.")
    void whenCertificationInfoMismatch_thenThrowException() {
        CheckCertificationRequestDto  requestDto = new CheckCertificationRequestDto();
        requestDto.setId("test00");
        requestDto.setEmail("test00@email.com");
        requestDto.setCertificationNumber("12345");
        // Given
        Certification wrongCertification = new Certification("user123", "wrong@example.com", "654321");
        when(certificationRepository.findByUserId(requestDto.getId())).thenReturn(wrongCertification);

        // When & Then
        assertThrows(CustomException.class, () -> authService.emailCertificationCheck(requestDto), "Should throw CertificationMismatch exception");
    }





}
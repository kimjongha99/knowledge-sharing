package com.hanghae.knowledgesharing.service.impl;


import com.hanghae.knowledgesharing.common.CertificationNumber;
import com.hanghae.knowledgesharing.dto.request.auth.*;
import com.hanghae.knowledgesharing.dto.response.ResponseDto;
import com.hanghae.knowledgesharing.dto.response.auth.*;
import com.hanghae.knowledgesharing.entity.Certification;
import com.hanghae.knowledgesharing.entity.User;
import com.hanghae.knowledgesharing.provider.EmailProvider;
import com.hanghae.knowledgesharing.provider.JwtProvider;
import com.hanghae.knowledgesharing.repository.CertificationRepository;
import com.hanghae.knowledgesharing.repository.UserRepository;
import com.hanghae.knowledgesharing.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private  final UserRepository userRepository;
    private final CertificationRepository certificationRepository;
    private  final JwtProvider  jwtProvider;
    private  final EmailProvider emailProvider;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckRequestDto idCheckRequestDto) {
        try {
            String userId = idCheckRequestDto.getId();
            boolean isExistId = userRepository.existsByUserId(userId);
            if(isExistId) return IdCheckResponseDto.duplicateId();



        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return IdCheckResponseDto.success();
    }

    @Override
    public ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto) {
        try {
            String userId = dto.getId();
            String email = dto.getEmail();
            boolean isExistId = userRepository.existsByUserId(userId);
            if(isExistId) return EmailCertificationResponseDto.duplicateId();
            String certificationNumber = CertificationNumber.getCertificationNumber(); // 임의의 4자리수 받아오기
            boolean succeeded = emailProvider.sendCertificationMail(email, certificationNumber);
            if(!succeeded) return EmailCertificationResponseDto.mailSendFail();


            Certification certification = new Certification(userId,email,certificationNumber);
            certificationRepository.save(certification);


        }catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }


        return EmailCertificationResponseDto.success();
    }

    @Override
    public ResponseEntity<? super CheckCertificationResponseDto> emailCertificationCheck(CheckCertificationRequestDto checkCertificationRequestDto) {
        try {
            String userId = checkCertificationRequestDto.getId();
            String email = checkCertificationRequestDto.getEmail();
            String certificationNumber = checkCertificationRequestDto.getCertificationNumber();

            Certification certification = certificationRepository.findByUserId(userId);
            if(certification == null) return CheckCertificationResponseDto.certificationFail();
            boolean isMatched = certification.getEmail().equals(email) && certification.getCertificationNumber().equals(certificationNumber);
            if(!isMatched) return CheckCertificationResponseDto.certificationFail();


        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return CheckCertificationResponseDto.success();

    }

    @Override
    public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {
        try {
            String userId = dto.getId();
            boolean isExistId = userRepository.existsByUserId(userId);
            if(isExistId) return SignUpResponseDto.duplicateId();

            String email = dto.getEmail();

            String certificationNumber = dto.getCertificationNumber();

            Certification certification = certificationRepository.findByUserId(userId);
            boolean isMatched = certification.getEmail().equals(email) && certification.getCertificationNumber().equals(certificationNumber);
            if(!isMatched) return SignUpResponseDto.certificationFail();


            String password = dto.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            dto.setPassword(encodedPassword);

            User user = new User(dto);
            userRepository.save(user);


            certificationRepository.deleteByUserId(userId);


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return SignUpResponseDto.success();

    }

    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {

        String token = null;

        try {

            String userId = dto.getId();
            User user = userRepository.findByUserId(userId);
            if(user == null) return SignInResponseDto.signInFail();

            String password = dto.getPassword();
            String encodedPassword = user.getPassword();
            boolean isMatched = passwordEncoder.matches(password, encodedPassword);
            if(!isMatched) return SignInResponseDto.signInFail();

            token = jwtProvider.create(userId);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignInResponseDto.success(token);

    }

}

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
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto, HttpServletResponse response) {

        String accessToken = null;
        String refreshToken =null;
        int expirationTime = 3600;

        try {

            String userId = dto.getId();
            User user = userRepository.findByUserId(userId);
            if(user == null) return SignInResponseDto.signInFail();

            String password = dto.getPassword();
            String encodedPassword = user.getPassword();
            boolean isMatched = passwordEncoder.matches(password, encodedPassword);
            if(!isMatched) return SignInResponseDto.signInFail();

             accessToken = jwtProvider.create(userId);
             refreshToken = jwtProvider.createRefreshToken(userId);

            Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
            accessTokenCookie.setHttpOnly(false);
            accessTokenCookie.setSecure(false); // Note: Set to false if you are testing over HTTP in development environment, true for production
            accessTokenCookie.setPath("/");
            accessTokenCookie.setMaxAge(3600); // Expiration time should match the JWT expiration
            response.addCookie(accessTokenCookie);

            Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
            refreshTokenCookie.setHttpOnly(false);
            refreshTokenCookie.setSecure(false); // Note: Set to false if you are testing over HTTP in development environment, true for production
            refreshTokenCookie.setPath("/");
            refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days for refresh token
            response.addCookie(refreshTokenCookie);


            user.setRefreshToken(refreshToken);
            userRepository.save(user);



        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignInResponseDto.success(accessToken,refreshToken,expirationTime);

    }


    //1. RefreshRequestDto 에서 리프래쉬토큰을 가져온다.
    //2. jwtProvider에 리프래쉬 토큰 검증 메서드를 만들고 검증한다.
    //3. 리프래쉬토큰이 만료된 토큰이거나 잘못된토큰이면 refreshInExpired 이 에러를 날린다.
    //4. 그게아닌 다른이유면 REFRESH_TOKEN_INVALID 이 애러를 날린다
    //5. 둘다 검증후 아니면 새로운 액세스토큰을 발급한다.
    //6.     public static ResponseEntity<RefreshResponseDto> success(String newAccessToken) { 여기에 담아서 리턴한다.
    @Override
    public ResponseEntity<? super RefreshResponseDto> refreshAccessToken(RefreshRequestDto requestBody, HttpServletResponse response) {
        String refreshToken = requestBody.getRefreshToken();
        String newAccessToken =null;
        try {
            // 새로고침 토큰의 유효성을 검사합니다.
            String userId = jwtProvider.validateRefreshToken(refreshToken);
            if (userId == null) return RefreshResponseDto.refreshInExpired();
            // 새로 고침 토큰이 데이터베이스의 토큰과 일치하는지 확인합니다.
            User user = userRepository.findByUserId(userId);
            if (user == null || !user.getRefreshToken().equals(refreshToken)) //리프래쉬토큰이 일치하지않거나 유저정보가없으면
                return RefreshResponseDto.refreshInFail();

            // 새로운 액세스 토큰을 생성합니다.
            newAccessToken = jwtProvider.create(userId);

            Cookie accessTokenCookie = new Cookie("accessToken", newAccessToken);
            accessTokenCookie.setHttpOnly(false);
            accessTokenCookie.setSecure(false); // Note: Set to false if you are testing over HTTP in development environment, true for production
            accessTokenCookie.setPath("/");
            accessTokenCookie.setMaxAge(3600); // Expiration time should match the JWT expiration
            response.addCookie(accessTokenCookie);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return RefreshResponseDto.success(newAccessToken);
        }

}

package com.hanghae.knowledgesharing.auth.service.impl;


import com.hanghae.knowledgesharing.auth.dto.request.auth.*;
import com.hanghae.knowledgesharing.auth.dto.response.auth.RefreshResponseDto;
import com.hanghae.knowledgesharing.auth.dto.response.auth.SignInResponseDto;
import com.hanghae.knowledgesharing.auth.repository.CertificationRepository;
import com.hanghae.knowledgesharing.auth.service.AuthService;
import com.hanghae.knowledgesharing.common.entity.Certification;
import com.hanghae.knowledgesharing.common.entity.User;
import com.hanghae.knowledgesharing.common.exception.CustomException;
import com.hanghae.knowledgesharing.common.exception.ErrorCode;
import com.hanghae.knowledgesharing.common.jwt.JwtProvider;
import com.hanghae.knowledgesharing.common.util.email.CertificationNumber;
import com.hanghae.knowledgesharing.common.util.email.EmailProvider;
import com.hanghae.knowledgesharing.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final CertificationRepository certificationRepository;
    private final JwtProvider jwtProvider;
    private final EmailProvider emailProvider;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();



    @Override
    @Transactional(readOnly = true)//   : 데이터를 변경하지 않고, 사용자 ID의 존재 여부만 확인합니다.
    public String idChecking(IdCheckRequestDto requestDto) {
        try {
            String userId = requestDto.getId();
            boolean isExistId = userRepository.existsByUserId(userId);
            if (isExistId) {
                throw new CustomException(ErrorCode.ExistId);
            }


        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.DatabaseError);
        }
        return "중복된 아이디가 없습니다.";


    }
    @Override
    @Transactional //이메일 인증 정보를 데이터베이스에 저장하는 작업이 포함되어 있으므로, readOnly 속성 없이 사용해야 합니다.
    public String emailCertification(EmailCertificationRequestDto dto) {
        String userId = dto.getId();
        String email = dto.getEmail();

        if (userRepository.existsByUserId(userId)) {
            throw new CustomException(ErrorCode.ExistId);
        }

        String certificationNumber = CertificationNumber.getCertificationNumber(); // Assume this generates a random code

        boolean succeeded = emailProvider.sendCertificationMail(email, certificationNumber);
        if (!succeeded) {
            throw new CustomException(ErrorCode.MailSendFail);
        }

        Certification certification = new Certification(userId, email, certificationNumber);
        certificationRepository.save(certification);

        return "메일전송 성공했습니다.";
    }

    // 이메일 인증번호 검증
    @Override
    @Transactional(readOnly = true)//체크(조회)만하는 작업이므로 readOnly true
    public String emailCertificationCheck(CheckCertificationRequestDto dto) {
            String userId = dto.getId();
            String email = dto.getEmail();
            String certificationNumber = dto.getCertificationNumber();


            Certification certification = certificationRepository.findByUserId(userId);
            if(certification == null){
                throw new CustomException(ErrorCode.CertificationNotFound);
            }
            boolean isMatched = certification.getEmail().equals(email) && certification.getCertificationNumber().equals(certificationNumber);
            if(!isMatched) {
                throw new CustomException(ErrorCode.CertificationMismatch);
            }

        return "인증이 성공적으로 완료되었습니다.";

    }



    @Override@Transactional

    public String signUp(SignUpRequestDto dto) {
        String userId = dto.getId();
        if (userRepository.existsByUserId(userId)) {
            throw new CustomException(ErrorCode.ExistId);
        }


        String email = dto.getEmail();

        String certificationNumber = dto.getCertificationNumber();

        Certification certification = certificationRepository.findByUserId(userId);


        boolean isMatched = certification.getEmail().equals(email) && certification.getCertificationNumber().equals(certificationNumber);
        if (!isMatched) {
            throw new CustomException(ErrorCode.CertificationMismatch);
        }

        String password = dto.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        dto.setPassword(encodedPassword);

        User user = new User(dto);
        userRepository.save(user);
        certificationRepository.deleteByUserId(userId);
        return "회원 가입이 완료되었습니다.";
    }


    @Override@Transactional
    public SignInResponseDto signIn(SignInRequestDto dto) {

            String userId = dto.getId();
            User user = userRepository.findByUserId(userId);
            if(user == null){
                throw new CustomException(ErrorCode.UserNotFound);
            };
            String password = dto.getPassword();
            String encodedPassword = user.getPassword();
            boolean isMatched = passwordEncoder.matches(password, encodedPassword);
            if(!isMatched) {
                throw new CustomException(ErrorCode.PasswordFail);
            };

             String  accessToken = jwtProvider.create(userId);
             String  refreshToken = jwtProvider.createRefreshToken(userId);
             int expirationTime = 3600;

           user.setRefreshToken(refreshToken);
             userRepository.save(user);

        SignInResponseDto responseDto = new SignInResponseDto(accessToken, refreshToken, expirationTime);
        return responseDto; // 성공 응답 생성 및 반환



    }



//    //1. RefreshRequestDto 에서 리프래쉬토큰을 가져온다.
//    //2. jwtProvider에 리프래쉬 토큰 검증 메서드를 만들고 검증한다.
//    //3. 리프래쉬토큰이 만료된 토큰이거나 잘못된토큰이면 refreshInExpired 이 에러를 날린다.
//    //4. 그게아닌 다른이유면 REFRESH_TOKEN_INVALID 이 애러를 날린다
//    //5. 둘다 검증후 아니면 새로운 액세스토큰을 발급한다.
//    //6.     public static ResponseEntity<RefreshResponseDto> success(String newAccessToken) { 여기에 담아서 리턴한다.
    @Override@Transactional
    public  RefreshResponseDto refreshAccessToken(RefreshRequestDto requestBody) {

        // 새로고침 토큰의 유효성을 검사합니다.
        String refreshToken = requestBody.getRefreshToken();
        String userId = jwtProvider.validateRefreshToken(refreshToken);
        if (userId == null) {
            throw new CustomException(ErrorCode.RefreshTokenExpire);
        }
        ;


        // 새로 고침 토큰이 데이터베이스의 토큰과 일치하는지 확인합니다.
        User user = userRepository.findByUserId(userId);
        if (user == null || !user.getRefreshToken().equals(refreshToken)) //리프래쉬토큰이 일치하지않거나 유저정보가없으
        {
            throw new CustomException(ErrorCode.RefreshTokenFail);
        }


        // 새로운 액세스 토큰을 생성합니다.
        String newAccessToken = jwtProvider.create(userId);
        RefreshResponseDto refreshResponse = new RefreshResponseDto(newAccessToken); // 생성자를 public으로 변경하거나, 팩토리 메소드/빌더 패턴 사용

        return refreshResponse;
    }


}
package com.hanghae.knowledgesharing.user.sevice.Impl;


import com.hanghae.knowledgesharing.article.repository.ArticleRepository;
import com.hanghae.knowledgesharing.cardSet.repository.CardSetRepository;
import com.hanghae.knowledgesharing.common.entity.Article;
import com.hanghae.knowledgesharing.common.entity.FlashcardSet;
import com.hanghae.knowledgesharing.common.entity.User;
import com.hanghae.knowledgesharing.common.exception.CustomException;
import com.hanghae.knowledgesharing.common.exception.ErrorCode;
import com.hanghae.knowledgesharing.user.dto.request.PatchPasswordRequestDto;
import com.hanghae.knowledgesharing.user.dto.request.PatchProfileImageRequestDto;
import com.hanghae.knowledgesharing.user.dto.response.*;
import com.hanghae.knowledgesharing.user.repository.UserRepository;
import com.hanghae.knowledgesharing.user.sevice.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ArticleRepository articleRepository;

    private final CardSetRepository cardSetRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();



    // 사용자 정보를 조회하여 반환합니다. 사용자가 없을 경우 예외를 발생시킵니다.
    @Override
    public GetSignInUserResponseDto getSignInUser(String userId) {
        User user = userRepository.findByUserId(userId);
        if(user == null){
            throw new CustomException(ErrorCode.UserNotFound);
        };

        GetSignInUserResponseDto responseDto = GetSignInUserResponseDto.fromUser(user); // User 엔티티로부터 GetSignInUserResponseDto를 생성합니다.
        return responseDto; // User 엔티티를 기반으로 응답 DTO를 생성하여 반환합니다.

    }


    // 사용자 정보를 조회하고 결과를 반환합니다.
    @Override
    public GetUserResponseDto  getUser(String userId) {
        User user = userRepository.findByUserId(userId);
        if(user == null){
            throw new CustomException(ErrorCode.UserNotFound);
        };

        GetUserResponseDto responseDto = GetUserResponseDto.fromUser(user); // User 엔티티로부터 GetSignInUserResponseDto를 생성합니다.

        return responseDto;
    }

    @Override
    public String patchProfileImage(PatchProfileImageRequestDto requestBody, String userId) {
        User user = userRepository.findByUserId(userId);
        if(user == null){
            throw new CustomException(ErrorCode.UserNotFound);
        };
        String profileImageUrl = requestBody.getProfileImageUrl();
        user.setProfileImageUrl(profileImageUrl);
        userRepository.save(user);


        return "프로필사진 수정이 완료되었습니다.";

    }

    @Override
    public String patchPassword(PatchPasswordRequestDto requestBody, String userId) {
        User user = userRepository.findByUserId(userId);
        if(user == null){
            throw new CustomException(ErrorCode.UserNotFound);
        };
        // 기존 비밀번호와 새 비밀번호를 비교합니다.
        if (passwordEncoder.matches(requestBody.getNewPassword(), user.getPassword())) {
            throw  new CustomException(ErrorCode.EqualPassword);
        }

        // 비밀번호가 다른 경우 새 비밀번호를 인코딩하여 저장합니다.
        user.setPassword(passwordEncoder.encode(requestBody.getNewPassword()));
        userRepository.save(user);

        return "비밀번호 수정이 완료되었습니다.";


    }

    @Override
    public UserArticleResponseDto getUserArticles(String userId, Pageable pageable) {
        User user = userRepository.findByUserId(userId);
        if(user == null){
            throw new CustomException(ErrorCode.UserNotFound);
        }

        Page<Article> articlePage = articleRepository.getUserArticles(userId, pageable);

        // 각 사용자 엔터티를 UserTypeInfoDto로 변환합니다.
        List<ArticleInfo> articleInfos = articlePage.getContent().stream()
                .map(article -> new ArticleInfo(article.getId(),article.getTitle(),article.getFavoriteCount(),article.getViewCount()))
                .collect(Collectors.toList());

        return  new UserArticleResponseDto(articleInfos,pageable.getPageNumber(), pageable.getPageSize(), articlePage.getTotalElements());
    }


    @Override
    public UserQuizResponseDto getUserQuiz(String userId, Pageable pageable) {
        User user = userRepository.findByUserId(userId);
        if(user == null){
            throw new CustomException(ErrorCode.UserNotFound);
        }
        Page<FlashcardSet> flashcardSetPage = cardSetRepository.getUserFlashCardSet(userId,pageable);

        // 플래시카드 세트 엔티티를 FlashcardSetSimpleDto로 변환
        List<UserQuizResponseDto.FlashcardSetSimpleDto> flashcardSetDtos = flashcardSetPage.getContent().stream()
                .map(flashcardSet -> new UserQuizResponseDto.FlashcardSetSimpleDto(
                        flashcardSet.getId(),
                        flashcardSet.getTitle(),
                        flashcardSet.getDescription() // content 필드는 description으로 가정
                ))
                .collect(Collectors.toList());


        // UserQuizResponseDto 생성 및 반환
        return new UserQuizResponseDto(
                flashcardSetDtos,
                flashcardSetPage.getNumber(),
                flashcardSetPage.getSize(),
                flashcardSetPage.getTotalElements()
        );

    }





}

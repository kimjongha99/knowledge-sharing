package com.hanghae.knowledgesharing.user.sevice;


import com.hanghae.knowledgesharing.user.dto.request.PatchPasswordRequestDto;
import com.hanghae.knowledgesharing.user.dto.request.PatchProfileImageRequestDto;
import com.hanghae.knowledgesharing.user.dto.response.GetSignInUserResponseDto;
import com.hanghae.knowledgesharing.user.dto.response.GetUserResponseDto;
import com.hanghae.knowledgesharing.user.dto.response.UserArticleResponseDto;
import org.springframework.data.domain.Pageable;


public interface UserService {


    GetSignInUserResponseDto getSignInUser(String userId);

    // 사용자 정보를 조회하고 결과를 반환합니다.
    GetUserResponseDto getUser(String userId);

    String patchProfileImage(PatchProfileImageRequestDto requestBody, String userId);

    String patchPassword(PatchPasswordRequestDto requestBody, String userId);

    UserArticleResponseDto getUserArticles(String userId, Pageable pageable);


}

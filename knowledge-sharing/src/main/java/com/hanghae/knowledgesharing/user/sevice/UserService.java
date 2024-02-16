package com.hanghae.knowledgesharing.user.sevice;


import com.hanghae.knowledgesharing.common.dto.ResponseDto;
import com.hanghae.knowledgesharing.user.dto.request.PatchPasswordRequestDto;
import com.hanghae.knowledgesharing.user.dto.request.PatchProfileImageRequestDto;
import com.hanghae.knowledgesharing.user.dto.response.GetSignInUserResponseDto;
import com.hanghae.knowledgesharing.user.dto.response.GetUserResponseDto;

public interface UserService {


    ResponseDto<GetSignInUserResponseDto> getSignInUser(String userId);

    // 사용자 정보를 조회하고 결과를 반환합니다.
    ResponseDto<GetUserResponseDto> getUser(String userId);

    ResponseDto<String> patchProfileImage(PatchProfileImageRequestDto requestBody, String userId);

    ResponseDto<String> patchPassword(PatchPasswordRequestDto requestBody, String userId);
}

package com.hanghae.knowledgesharing.user.dto.response.user;

import com.hanghae.knowledgesharing.common.exception.ResponseCode;
import com.hanghae.knowledgesharing.common.exception.ResponseMessage;
import com.hanghae.knowledgesharing.common.dto.ResponseDto;
import com.hanghae.knowledgesharing.common.entity.User;
import com.hanghae.knowledgesharing.common.enums.UserRoleEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class GetSignInUserResponseDto extends ResponseDto {

    private String userId;
    private String email;
    private String profileImageUrl;

    private UserRoleEnum role;

    private String type;

    private GetSignInUserResponseDto(User user) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.profileImageUrl = user.getProfileImageUrl();
        this.role= user.getRole();
        this.type = user.getType();

    }

    public static ResponseEntity<GetSignInUserResponseDto> success(User user) {
        GetSignInUserResponseDto result = new GetSignInUserResponseDto(user);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> notExistUser () {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }
    }

package com.hanghae.knowledgesharing.dto.response.user;

import com.hanghae.knowledgesharing.common.ResponseCode;
import com.hanghae.knowledgesharing.common.ResponseMessage;
import com.hanghae.knowledgesharing.dto.response.ResponseDto;
import com.hanghae.knowledgesharing.entity.User;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class GetUserResponseDto extends ResponseDto {

    private String userId;
    private String email;
    private String profileImageUrl;

    private String type;

    private GetUserResponseDto(User user) {
        super();
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.profileImageUrl = user.getProfileImageUrl();
        this.type = user.getType();
    }

    public static ResponseEntity<GetUserResponseDto> success(User user) {
        GetUserResponseDto result = new GetUserResponseDto(user);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> noExistUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
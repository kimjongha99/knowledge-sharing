package com.hanghae.knowledgesharing.dto.response.auth;


import com.hanghae.knowledgesharing.common.ResponseCode;
import com.hanghae.knowledgesharing.common.ResponseMessage;
import com.hanghae.knowledgesharing.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class RefreshResponseDto extends ResponseDto {

    private String newAccessToken;

    private  int expirationTime;

    private RefreshResponseDto(String newAccessToken) {
        super();
        this.newAccessToken=  newAccessToken;
        this.expirationTime=3600;
    }
    public static ResponseEntity<RefreshResponseDto> success(String newAccessToken) {
        RefreshResponseDto responseBody = new RefreshResponseDto(newAccessToken);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> refreshInFail() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.REFRESH_TOKEN_INVALID, ResponseMessage.REFRESH_TOKEN_INVALID);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }
    public static ResponseEntity<ResponseDto> refreshInExpired() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.REFRESH_TOKEN_EXPIRED, ResponseMessage.REFRESH_TOKEN_EXPIRED);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }
}

package com.hanghae.knowledgesharing.dto.response.article;


import com.hanghae.knowledgesharing.common.ResponseCode;
import com.hanghae.knowledgesharing.common.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class UpdateFavoriteCountResponseDto {

    private String code;
    private  String message;

    public UpdateFavoriteCountResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public static ResponseEntity<UpdateFavoriteCountResponseDto> success() {
        UpdateFavoriteCountResponseDto response = new UpdateFavoriteCountResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    public static ResponseEntity<UpdateFavoriteCountResponseDto> databaseError() {
        UpdateFavoriteCountResponseDto response = new UpdateFavoriteCountResponseDto(ResponseCode.DATABASE_ERROR, ResponseMessage.DATABASE_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}

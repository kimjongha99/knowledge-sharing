package com.hanghae.knowledgesharing.user.dto.response.user;


import com.hanghae.knowledgesharing.common.exception.ResponseCode;
import com.hanghae.knowledgesharing.common.exception.ResponseMessage;
import com.hanghae.knowledgesharing.common.dto.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class PatchPasswordResponseDto extends ResponseDto {

    private PatchPasswordResponseDto(){
        super();
    }

    public static ResponseEntity<PatchPasswordResponseDto> success() {
        PatchPasswordResponseDto responseBody = new PatchPasswordResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    public static ResponseEntity<ResponseDto> previousPassword() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.PREVIOUS_PASSWORD, ResponseMessage.PREVIOUS_PASSWORD);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
    public static ResponseEntity<ResponseDto> noExistUser() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}

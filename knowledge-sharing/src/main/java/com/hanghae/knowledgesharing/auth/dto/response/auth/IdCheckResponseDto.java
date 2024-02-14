package com.hanghae.knowledgesharing.auth.dto.response.auth;


import com.hanghae.knowledgesharing.common.exception.ResponseCode;
import com.hanghae.knowledgesharing.common.exception.ResponseMessage;
import com.hanghae.knowledgesharing.common.dto.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class IdCheckResponseDto  extends ResponseDto {

    private  IdCheckResponseDto(){
        super(); // 부모의 생성자를 호출한다.
    }

    public static ResponseEntity<IdCheckResponseDto> success() {
        IdCheckResponseDto responseBody = new IdCheckResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    public static ResponseEntity<ResponseDto> duplicateId() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.DUPLICATION_ID, ResponseMessage.DUPLICATION_ID);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }



}

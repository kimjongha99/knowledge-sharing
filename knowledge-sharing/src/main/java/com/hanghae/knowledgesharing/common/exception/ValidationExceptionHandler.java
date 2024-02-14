package com.hanghae.knowledgesharing.common.exception;


import com.hanghae.knowledgesharing.common.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice  //컨트롤러 전역에 걸쳐 예외를 처리하는데사용
public class ValidationExceptionHandler {

    //MethodArgumentNotValidException: 이 예외는 HTTP 요청의 메소드 인자가 유효하지 않을 때 발생합니다. 예를 들어, 요청 본문에 필요한 필드가 누락되었거나 잘못된 형식일 때 이 예외가 발생할 수 있습니다.
    //HttpMessageNotReadableException: 이 예외는 요청 본문을 읽고 파싱하는 과정에서 문제가 발생했을 때 발생합니다. 예를 들어, JSON 형식의 오류나 파싱할 수 없는 데이터가 포함되어 있을 때 이 예외가 발생합니다.
    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ResponseDto> validationExceptionHandler(Exception exception) {
        return ResponseDto.validationFail(); // 위 두개의 익셉션이 발생시 validationFail라는 응답을 주게됌
    }
}

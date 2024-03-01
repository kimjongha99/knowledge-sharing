package com.hanghae.knowledgesharing.common.exception;

import com.hanghae.knowledgesharing.common.dto.ResponseDto;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

/*
전체 애플리케이션에대한 예외처리를 전역적으로 처리하는 클래스  =@ControllerAdvice

handlerException = 클라이언트에게 노출할 에러 코드, 메세지




 */

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseDto.Error> handlerException(CustomException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(new ResponseDto.Error(e.getErrorCode().getMessage()));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class) //이거는 유효성검사 커스텀
    public ResponseEntity<ResponseDto.Error> validationException(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return ResponseEntity.status(ErrorCode.InValidException.getHttpStatus())
                .body(new ResponseDto.Error(errors.get(0)));
    }

}


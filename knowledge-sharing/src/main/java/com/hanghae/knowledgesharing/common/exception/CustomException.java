package com.hanghae.knowledgesharing.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;



/*
이클래스는 런타임익셉션 을 확장하며 커스텀 예외를 처리함.
 */
@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;  //Enum 으로 처리


    @Getter
    @RequiredArgsConstructor
    public static class ExcelError extends RuntimeException {
        private final String numberMessage;
        private final ErrorCode errorCode;
    }
}
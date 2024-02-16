package com.hanghae.knowledgesharing.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {
    private int statusCode;

    //JSON으로 직렬화할 때 null 값을 제외하기 위해
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static <T> ResponseDto<T> success(T data) {

        return new ResponseDto<>(200, data);
    }

    @Getter
    @AllArgsConstructor
    public static class Error {
        private final String errorMessage;
    }
}

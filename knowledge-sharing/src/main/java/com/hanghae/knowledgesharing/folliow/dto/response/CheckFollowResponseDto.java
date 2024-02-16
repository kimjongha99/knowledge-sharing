package com.hanghae.knowledgesharing.folliow.dto.response;

import com.hanghae.knowledgesharing.common.enums.CheckFollowEnum;
import lombok.Getter;


@Getter
public class CheckFollowResponseDto {

    private String message;

    private CheckFollowEnum checkFollowEnum;


    public CheckFollowResponseDto(String message, CheckFollowEnum checkFollowEnum) {
        this.message = message;
        this.checkFollowEnum = checkFollowEnum;
    }
}

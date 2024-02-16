package com.hanghae.knowledgesharing.auth.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class IdCheckRequestDto {


    @NotBlank
    private String id;

}

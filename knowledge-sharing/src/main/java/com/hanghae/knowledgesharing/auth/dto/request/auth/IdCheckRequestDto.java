package com.hanghae.knowledgesharing.auth.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IdCheckRequestDto {


    @NotBlank
    private String id;

}

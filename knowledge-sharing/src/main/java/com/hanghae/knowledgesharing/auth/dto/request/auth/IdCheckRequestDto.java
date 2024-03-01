package com.hanghae.knowledgesharing.auth.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IdCheckRequestDto {


    @NotBlank
    private String id;

}

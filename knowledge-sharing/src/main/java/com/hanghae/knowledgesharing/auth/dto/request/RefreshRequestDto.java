package com.hanghae.knowledgesharing.auth.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RefreshRequestDto {

    @NotBlank
    String refreshToken;
}

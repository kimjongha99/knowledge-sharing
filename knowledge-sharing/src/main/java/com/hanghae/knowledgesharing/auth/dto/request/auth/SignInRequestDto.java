package com.hanghae.knowledgesharing.auth.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignInRequestDto {
    @NotBlank // 아이디는 비어 있으면 안 됩니다.
    private String id;
    @NotBlank // 비밀번호는 비어 있으면 안 됩니다.
    private String password;
}

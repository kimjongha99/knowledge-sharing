package com.hanghae.knowledgesharing.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchPasswordRequestDto {

    private String newPassword;

}

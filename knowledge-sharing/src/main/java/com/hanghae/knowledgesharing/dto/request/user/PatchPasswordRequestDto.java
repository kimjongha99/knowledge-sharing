package com.hanghae.knowledgesharing.dto.request.user;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchPasswordRequestDto {

    private String newPassword;


}

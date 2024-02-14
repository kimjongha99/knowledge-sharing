package com.hanghae.knowledgesharing.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequestDto {
  
  @NotBlank
  private String id;

  @NotBlank
//  @Pattern(regexp="^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{8,13}$")  테스트를 편하게 하기위해
  private String password;


  @NotBlank
  @Email
  private String email;
  
  @NotBlank
  private String certificationNumber;


}

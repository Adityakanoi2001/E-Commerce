package com.example.UserModule.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignInDto {
  private String email;
  private String password;

}

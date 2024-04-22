package com.example.UserModule.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignInResponseDto {
  private String status;
  private String token;
}

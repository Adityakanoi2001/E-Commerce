package com.example.UserModule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDeactivationResponseDto {
  private boolean accountDeactivated;
  private String response;

}

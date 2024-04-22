package com.example.UserModule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDeletionResponseDto {
  private boolean accountDeleted;
  private String response;

}

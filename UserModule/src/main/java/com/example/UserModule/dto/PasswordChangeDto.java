package com.example.UserModule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordChangeDto {
  String newPassword;
  String conformPassword;
  String uniqueTokenId;
}

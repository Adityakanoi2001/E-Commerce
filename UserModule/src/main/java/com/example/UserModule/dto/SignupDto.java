package com.example.UserModule.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupDto {
  private String firstName;
  private String lastName;
  private String usn;
  private String email;
  private String password;
  private String phoneNumber;
  private String city;
  private String state;
}

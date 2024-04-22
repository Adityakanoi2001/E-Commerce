package com.example.UserModule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

// Class
public class EmailDetails {

  // Class data members
  private String recipient;
  private String msgBody;
  private String subject;
  private String attachment;
}

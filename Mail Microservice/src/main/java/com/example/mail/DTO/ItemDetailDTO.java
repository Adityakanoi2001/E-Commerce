package com.example.mail.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDetailDTO {
  private String name;
  private String quantity;
  private double rate;
  private double amount;
}

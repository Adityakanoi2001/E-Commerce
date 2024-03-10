package com.example.mail.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDetailEntity {
  private String name;
  private String quantity;
  private double rate;
  private double amount;
}

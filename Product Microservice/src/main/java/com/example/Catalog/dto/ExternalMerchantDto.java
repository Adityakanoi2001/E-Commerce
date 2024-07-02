package com.example.Catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ExternalMerchantDto {
  private String merchantId;
  private String merchantName;
  private String merchantCity;
  private String merchantState;
  private String company;
}

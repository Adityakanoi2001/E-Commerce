package com.example.Catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewInputDto {
  private String userId;
  private String productSkuId;
  private String reviewContent;
}

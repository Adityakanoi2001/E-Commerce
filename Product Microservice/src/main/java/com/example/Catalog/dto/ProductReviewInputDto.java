package com.example.Catalog.dto;

import lombok.Data;

@Data
public class ProductReviewInputDto {
  private String userId;
  private String productSkuId;
  private String reviewContent;
}

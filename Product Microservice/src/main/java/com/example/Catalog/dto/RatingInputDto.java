package com.example.Catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingInputDto {
  private String productSkuId;
  private Integer ratingValue;
}

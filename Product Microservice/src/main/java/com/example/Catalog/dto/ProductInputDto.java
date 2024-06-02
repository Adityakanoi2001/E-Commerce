package com.example.Catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInputDto {
  private String productName;
  private String productDescription;
  private Binary productImages;
  private String categoryId;
  private String merchantId;
  private double price;
  private int stock;
  private String brand;
}

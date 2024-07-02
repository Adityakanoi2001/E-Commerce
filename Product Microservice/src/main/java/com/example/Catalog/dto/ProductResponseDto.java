package com.example.Catalog.dto;

import com.example.Catalog.entities.Reviews;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {
  private String productSkuId;
  private String productName;
  private String categoryId;
  private String productDescription;
  private Binary productImages;
  private List<ExternalMerchantDto> merchants;
  private HashMap<ExternalMerchantDto,Double> price;
  private int stock;
  private String brand;
  private int rating;
  private int saleCount;
  private HashMap<String,Reviews> reviews;
  private boolean activeStatus;
}

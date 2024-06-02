package com.example.Catalog.entities;

import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "BliCommerceProducts")
public class ProductsEntity {

  @Id
  private String productSkuId;
  private String productName;
  private String categoryId;
  private String productDescription;
  private Binary imageURL;
  private List<String> merchantId;
  private String merchantName;
  private double price;
  private int stock;
  private String brand;
  private int rating = 5;
  private int noOfBuyers = 1;
  private List<String> reviewId;
}




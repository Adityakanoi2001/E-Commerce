package com.example.Catalog.entities;

import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashMap;
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
  private Binary productImages;
  private List<String> merchantId;
  private HashMap<String,Double> price;
  private int stock;
  private int reservedStock;
  private int saleStock;
  private String brand;
  private int rating = 5;
  private int saleCount = 0;
  private List<String> reviewId;
  private boolean activeStatus;
  private Date dateAdded;
  private Date dateModified;
}




package com.example.MerchantMongo.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ProductDto {

    private String productId;
    private String productName;
    private String categoryId;
    private String productDescription;
    private String imageURL;
    private String merchantId;
    private String merchantName;
    private double price;
}

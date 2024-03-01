package com.example.MerchantMongo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;


@Data
@Getter
@Setter
public class ProductEntity {
    @Id
    private String productId;

    private String productName;

    private String categoryId;

    private String productDescription;

    private String imageURL;

    private String merchantId;

    private String merchantName;

    private double price;
}

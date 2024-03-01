package com.example.CartEcommerce.entites;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Getter
@Setter
@Document
public class ProductsEntity {

    @Id
    private String productId;
    private String productName;
    private String brand;
    private String categoryId;
    private String productDescription;
    private String imageURL;
    private String merchantId;
    private String merchantName;
    private double price;
    private int stock;
    private int quantity;   //this is the qunattity for each item user adds written as stock

}
